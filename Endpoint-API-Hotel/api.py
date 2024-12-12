from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import pandas as pd
import numpy as np
import tensorflow as tf
from sklearn.preprocessing import OneHotEncoder, StandardScaler, LabelEncoder
from fastapi.responses import JSONResponse

# Initialize FastAPI
app = FastAPI()

# Load the dataset
hotels_df = pd.read_csv("https://raw.githubusercontent.com/MuhammadYusufAndrika/tripskuy-capstone/refs/heads/main/merged_hotels_data.csv")

# Prepare features and target
X = hotels_df[['price', 'rating', 'num_reviews', 'star_rating', 'city']]
y = hotels_df['category']

# Encode the 'city' column using OneHotEncoder
city_encoder = OneHotEncoder(sparse_output=False)
city_encoded = city_encoder.fit_transform(X[['city']])
city_encoded_df = pd.DataFrame(city_encoded, columns=city_encoder.get_feature_names_out(['city']))

# Combine encoded city data with other numeric features
X = pd.concat([X[['price', 'rating', 'num_reviews', 'star_rating']], city_encoded_df], axis=1)

# Encode the category (target)
label_encoder = LabelEncoder()
y_encoded = label_encoder.fit_transform(y)

# Standardize numeric features
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Load the pre-trained model
model = tf.keras.models.load_model("hotel_rating_predictor.h5")

# Function to recommend hotels
def recommend_hotel(user_location: str, user_budget: float):
    # Filter hotels based on the location
    if user_location not in hotels_df['city'].values:
        return {"message": "Location not found in the dataset."}

    filtered_hotels = hotels_df[hotels_df['city'] == user_location]

    # Filter hotels within the budget
    affordable_hotels = filtered_hotels[filtered_hotels['price'] <= user_budget]
    if affordable_hotels.empty:
        return {"message": "No hotels match your budget in this location."}

    affordable_hotels = affordable_hotels.reset_index(drop=True)

    # Prepare data for prediction
    affordable_features = affordable_hotels[['price', 'rating', 'num_reviews', 'star_rating']]
    city_encoded = city_encoder.transform(affordable_hotels[['city']])
    city_encoded_df = pd.DataFrame(city_encoded, columns=city_encoder.get_feature_names_out(['city']))

    affordable_features = pd.concat([affordable_features, city_encoded_df], axis=1)
    affordable_features_scaled = scaler.transform(affordable_features)

    # Predict categories
    affordable_predictions = model.predict(affordable_features_scaled)
    predicted_categories = label_encoder.inverse_transform(np.argmax(affordable_predictions, axis=1))

    affordable_hotels['category_hotel'] = predicted_categories
    sorted_hotels = affordable_hotels.sort_values(by='price', ascending=False)

    return sorted_hotels[['name_hotel', 'price', 'category_hotel','hotel_features', 'star_rating', 'coordinates', 'url_image']].to_dict(orient='records')

# Define the schema for POST requests
class RecommendationRequest(BaseModel):
    location: str
    budget: float

# GET endpoint
@app.get("/recommendations/")
async def get_recommendations(location: str, budget: float):
    result = recommend_hotel(location, budget)
    if "message" in result:
        raise HTTPException(status_code=404, detail=result["message"])
    return JSONResponse(content={"recommendations": result})

# POST endpoint
@app.post("/recommendations/")
async def post_recommendations(req: RecommendationRequest):
    result = recommend_hotel(req.location, req.budget)
    if "message" in result:
        raise HTTPException(status_code=404, detail=result["message"])
    return JSONResponse(content={"recommendations": result})
