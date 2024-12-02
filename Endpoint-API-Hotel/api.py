from fastapi import FastAPI
from pydantic import BaseModel
import pandas as pd
import numpy as np
import tensorflow as tf
from sklearn.preprocessing import OneHotEncoder, StandardScaler, LabelEncoder

# Inisialisasi FastAPI
app = FastAPI()

# Membaca data
hotels_df = pd.read_csv("https://raw.githubusercontent.com/MuhammadYusufAndrika/tripskuy-capstone/refs/heads/main/merged_hotels_data.csv")

# Fitur dan target
X = hotels_df[['price', 'rating', 'num_reviews', 'star_rating', 'city']]
y = hotels_df['category']

# Encode kolom 'city' menggunakan OneHotEncoder
city_encoder = OneHotEncoder(sparse_output=False)
city_encoded = city_encoder.fit_transform(X[['city']])
city_encoded_df = pd.DataFrame(city_encoded, columns=city_encoder.get_feature_names_out(['city']))

# Gabungkan hasil OneHotEncoder dengan fitur numerik lainnya
X = pd.concat([X[['price', 'rating', 'num_reviews', 'star_rating']], city_encoded_df], axis=1)

# Encode kolom kategori (target) menjadi numerik
label_encoder = LabelEncoder()
y_encoded = label_encoder.fit_transform(y)

# Standarisasi fitur numerik
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Load model yang telah disimpan
model = tf.keras.models.load_model("hotel_rating_predictor.h5")

# Pydantic model untuk menerima input dari pengguna
class UserInput(BaseModel):
    location: str
    budget: float

# Fungsi rekomendasi hotel
def recommend_hotel(user_location: str, user_budget: float):
    filtered_hotels = hotels_df[hotels_df['city'] == user_location] if user_location in hotels_df['city'].values else None

    if filtered_hotels is not None:
        affordable_hotels = filtered_hotels[filtered_hotels['price'] <= user_budget]

        if not affordable_hotels.empty:
            affordable_hotels = affordable_hotels.reset_index(drop=True)

            affordable_features = affordable_hotels[['price', 'rating', 'num_reviews', 'star_rating']]
            city_encoded = city_encoder.transform(affordable_hotels[['city']])
            city_encoded_df = pd.DataFrame(city_encoded, columns=city_encoder.get_feature_names_out(['city']))

            affordable_features = pd.concat([affordable_features, city_encoded_df], axis=1)
            affordable_features_scaled = scaler.transform(affordable_features)

            affordable_predictions = model.predict(affordable_features_scaled)
            predicted_categories = label_encoder.inverse_transform(np.argmax(affordable_predictions, axis=1))

            affordable_hotels['category_hotel'] = predicted_categories
            sorted_hotels = affordable_hotels.sort_values(by='price', ascending=False)

            return sorted_hotels[['name_hotel', 'price', 'category_hotel', 'star_rating', 'coordinates', 'url_image']].to_dict(orient='records')
        else:
            return {"message": "Tidak ada hotel yang sesuai dengan budget Anda."}
    else:
        return {"message": "Lokasi tidak ditemukan dalam dataset."}

# Endpoint untuk rekomendasi hotel
@app.post("/recommendations/")
async def get_recommendations(user_input: UserInput):
    result = recommend_hotel(user_input.location, user_input.budget)
    return {"recommendations": result}

# Menjalankan server (Jalankan perintah berikut di terminal)
# uvicorn api:app --reload
