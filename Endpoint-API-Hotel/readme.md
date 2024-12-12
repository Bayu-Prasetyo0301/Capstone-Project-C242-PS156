## **1. Hotel Recommendation Service**

### **Description**
This service recommends hotels based on the user's preferred location and budget. The system uses a neural network to predict the category of hotels and filters options accordingly.

### **Main Features**
- Filters hotels by location and budget.
- Encodes data such as cities and categories.
- Standardizes input features for model predictions.
- Returns a list of top recommended hotels sorted by price.

### **Dataset Used**
- **CSV File:** `merged_hotels_data.csv`
- Contains information about hotel prices, ratings, reviews, star ratings, and categories.

### **Model**
- **TensorFlow model:** `hotel_rating_predictor.h5`
- Predicts hotel categories based on input features.

---

## **2. Running the FastAPI Apps**

### **Pre-requisites**
- Python 3.8 or higher
- FastAPI
- Uvicorn
- TensorFlow
- Pandas, NumPy, Scikit-Learn

### **Installation Steps**
1. Clone the repository:
   ```
   git clone https://github.com/Bayu-Prasetyo0301/Capstone-Project-C242-PS156
   cd Capstone-Project-C242-PS156/Endpoint-API-Hotel/
   ```
2. Install dependencies:

```
pip install fastapi uvicorn pandas numpy tensorflow scikit-learn
```
3. Run the FastAPI server for either service:

```
uvicorn api:app --reload
```


## **3. API Endpoints

Hotel Recommendation Endpoint
URL: /recommendations/
Method: GET
Parameters:
location (str): The city to search for hotels.
budget (float): The maximum budget for a hotel.
Example Request:
```
curl "http://127.0.0.1:8000/recommendations/?location=Yogyakarta&budget=150000000"
```
Response Body:
```
{
  "recommendations": [
    {
      "name_hotel": "KIMAYA Sudirman Yogyakarta, By HARRIS",
      "price": 1498000,
      "category_hotel": "midrange",
      "hotel_features": "Layanan pijat, Pusat kebugaran, Aksesibel bagi penyandang disabilitas, Spa, Pusat kebugaran, Bar",
      "star_rating": 4,
      "coordinates": "-7.782718699999998, 110.3777969",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/130.jpg"
    },
    {
      "name_hotel": "The Jayakarta Yogyakarta Hotel & Spa",
      "price": 1456000,
      "category_hotel": "midrange",
      "hotel_features": "Ruang santai, Pemancingan, Bilyar, Fasilitas bisnis, Lapangan tennis outdoor, Bar di kolam renang, Aksesibel bagi penyandang disabilitas, Bar",
      "star_rating": 4,
      "coordinates": "-7.78345457624549, 110.419667959213",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/210.jpg"
    },
    {
      "name_hotel": "favehotel Malioboro - Yogyakarta",
      "price": 1439370,
      "category_hotel": "midrange",
      "hotel_features": "Layanan pijat, Spa",
      "star_rating": 3,
      "coordinates": "-7.785702, 110.369769",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/240.jpg"
    },
    {
      "name_hotel": "Greenhost Boutique Hotel",
      "price": 1412500,
      "category_hotel": "midrange",
      "hotel_features": "Restoran show cooking, Penyewaan sepeda, Sewa mobil, Aksesibel bagi penyandang disabilitas, Bar",
      "star_rating": 3,
      "coordinates": "-7.820081, 110.369119",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/78.jpg"
    },
    {
      "name_hotel": "Grand Kangen Hotel Urip Sumoharjo Yogyakarta",
      "price": 1401514,
      "category_hotel": "midrange",
      "hotel_features": "Sewa mobil, Layanan pijat, Spa",
      "star_rating": 3,
      "coordinates": "-7.7831141, 110.3869588",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/63.jpg"
    },
    {
      "name_hotel": "@HOM Premiere Timoho",
      "price": 1377000,
      "category_hotel": "midrange",
      "hotel_features": "Penitipan anak, Sewa mobil, Bar di kolam renang, Antar-jemput bandara, Aksesibel bagi penyandang disabilitas, Spa",
      "star_rating": 4,
      "coordinates": "-7.798514499999999, 110.39245589999996",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/1.jpg"
    },
    {
      "name_hotel": "Indoluxe Hotel Jogjakarta",
      "price": 1350000,
      "category_hotel": "midrange",
      "hotel_features": "Sewa mobil, Pusat kebugaran, Teras rooftop, Bar di kolam renang, Aksesibel bagi penyandang disabilitas, Spa, Pusat kebugaran, Bar",
      "star_rating": 4,
      "coordinates": "-7.750694, 110.371717",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/125.jpg"
    },
    {
      "name_hotel": "Next Hotel Yogyakarta",
      "price": 1281852,
      "category_hotel": "midrange",
      "hotel_features": "Bayar saat Check-in, Ruang santai, Bar, Pengering pakaian, Fasilitas bisnis, Bar di kolam renang, Aksesibel bagi penyandang disabilitas, Bar",
      "star_rating": 4,
      "coordinates": "-7.780946799999998, 110.418641",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/154.jpg"
    },
    {
      "name_hotel": "ibis Yogyakarta Adi Sucipto",
      "price": 1251566,
      "category_hotel": "midrange",
      "hotel_features": "Check-out ekspress",
      "star_rating": 3,
      "coordinates": "-7.783458, 110.392004",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/245.jpg"
    },
    {
      "name_hotel": "Horison Lynn Yogyakarta",
      "price": 1250000,
      "category_hotel": "midrange",
      "hotel_features": "Sewa mobil, Aksesibel bagi penyandang disabilitas",
      "star_rating": 3,
      "coordinates": "-7.82459, 110.366831",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/96.jpg"
    },
    {
      "name_hotel": "All Nite & Day Hotel Yogyakarta - Gejayan",
      "price": 1240000,
      "category_hotel": "midrange",
      "hotel_features": "Check-out ekspress",
      "star_rating": 3,
      "coordinates": "-7.7802114, 110.3879623",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/8.jpg"
    },
    {
      "name_hotel": "Grand Diamond Hotel Yogyakarta",
      "price": 1210370,
      "category_hotel": "midrange",
      "hotel_features": "Sewa mobil, Fasilitas bisnis",
      "star_rating": 4,
      "coordinates": "-7.782487, 110.423852",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/59.jpg"
    },
    {
      "name_hotel": "Yellow Star Ambarukmo Hotel",
      "price": 1151667,
      "category_hotel": "midrange",
      "hotel_features": "Penitipan anak, Sewa mobil, Layanan pijat, Spa",
      "star_rating": 3,
      "coordinates": "-7.783328000000002, 110.39758899999993",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/229.jpg"
    },
    {
      "name_hotel": "Yellow Star Gejayan Hotel",
      "price": 1109000,
      "category_hotel": "midrange",
      "hotel_features": "Sewa mobil, Teras rooftop, Aksesibel bagi penyandang disabilitas, Check-out ekspress",
      "star_rating": 3,
      "coordinates": "-7.77139, 110.389811",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/230.jpg"
    },
    {
      "name_hotel": "Hotel Dafam Fortuna Seturan",
      "price": 1029999,
      "category_hotel": "midrange",
      "hotel_features": "Penitipan anak, Sewa mobil, Layanan pijat, Aksesibel bagi penyandang disabilitas, Spa, Bar",
      "star_rating": 4,
      "coordinates": "-7.76561, 110.410881",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/109.jpg"
    },
    {
      "name_hotel": "Grand Sarila Hotel Yogyakarta",
      "price": 976666,
      "category_hotel": "midrange",
      "hotel_features": "Sewa mobil, Layanan pijat",
      "star_rating": 3,
      "coordinates": "-7.759019235289791, 110.39588570638443",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/70.jpg"
    },
    {
      "name_hotel": "Sagan Heritage Hotel",
      "price": 966667,
      "category_hotel": "midrange",
      "hotel_features": "Area main anak, Check-out ekspress",
      "star_rating": 3,
      "coordinates": "-7.77997875213623, 110.376335144043",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/174.jpg"
    },
    {
      "name_hotel": "Student Castle Apartment HD",
      "price": 878333,
      "category_hotel": "budget",
      "hotel_features": "Ruang santai, Bilyar, Pusat kebugaran, Check-out ekspress, Pusat kebugaran",
      "star_rating": 0,
      "coordinates": "-7.773638441955473, 110.40791000054412",
      "url_image": "https://storage.googleapis.com/picture-bucket19/hotel/189.jpg"
    }
  ]
}
```
Error Response:
```
{
  "detail": "Location not found in the dataset."
}
```

## **4. Dependencies

FastAPI: Web framework for building APIs.
Uvicorn: ASGI server for running FastAPI.
TensorFlow: For loading and running pre-trained models.
Pandas: Data manipulation and analysis.
NumPy: Numerical operations.
Scikit-Learn: Data preprocessing tools.
