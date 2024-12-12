## **1. Place Recommendation Service**

### **Description**
This service provides tourist destination recommendations based on category, city, and user-defined ticket price. The system predicts ratings and sorts recommendations by the closest price to the user's input.

### **Main Features**
- Encodes categorical data (city and category).
- Predicts place ratings using a trained model.
- Returns the top 10 tourist places based on ticket price proximity.

### **Dataset Used**
- **CSV File:** `mergefixfile.csv`
- Includes data about place categories, ticket prices, ratings, and descriptions.

### **Model**
- **TensorFlow model:** `tourism_model.keras`
- Predicts ratings for tourist destinations.

---

## **3. Running the FastAPI Apps**

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
   cd Capstone-Project-C242-PS156/Endpoint-API-Recommendations/
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

Place Recommendation Endpoint
URL: /recommendations/
Method: GET
Parameters:
category (str): The category of the place.
city (str): The city where the place is located.
ticket_price (float): Desired ticket price.
Example Request:
```
curl "https://rekomendasi-tempat-625541638853.asia-southeast2.run.app/recommendations/?category=Taman%20Hiburan&city=Yogyakarta&ticket_price=50000"
```
Place Recommendation Endpoint
URL: /recommendations/
Method: POST
Parameters:
category (str): The category of the place.
city (str): The city where the place is located.
ticket_price (float): Desired ticket price.
Example Request:
```
curl "http://127.0.0.1:8000/recommendations/"
```
Example JSON:
```
{
  "category:"Taman Hiburan""
  "city": "Yogyakarta",
  "ticket_price": 50000
}
```
Response of POST and GET:
```
{
    "predicted_rating": 227.47740173339844,
    "recommendations": [
        {
            "name_place": "Desa Wisata Tembi",
            "ticket_place": 50000,
            "rating_place": 4.5,
            "desc_place": "Desa Wisata Tembi, merupakan satu dari sekian desa wisata yang sukses menata kawasannya, sehingga menjadi sebuah kawasan yang layak untuk dikunjungi. Potensi alam, sejarah, serta potensi budaya yang dimiliki Desa Wisata Tembi memang sangat pas untuk dijadikan sebuah desa wisata. Apalagi jarak Desa Wisata Tembi tidak jauh dari pusat kota Jogja. Ternyata, pesona keindahan Desa Wisata Tembi tidak hanya terbatas pada dua hal tersebut saja. Dan inilah info lengkap Desa Wisata Tembi, sebagai referensi awal menentukan lokasi wisata saat liburan nanti. Lokasi Desa Wisata Tembi terletak satu arah menuju Parangtritis. Alamat Desa Wisata Tembi berada di Desa Tembi, Kecamatan Sewon, Kabupaten Bantul, Daerah Istimewa Yogyakarta.",
            "coordinate": {
                "lat": -7.872567300000001,
                "lng": 110.3546963
            },
            "url_image": "https://storage.googleapis.com/picture-bucket19/205.jpg"
        },
        {
            "name_place": "Galaxy Waterpark Jogja",
            "ticket_place": 40000,
            "rating_place": 4.3,
            "desc_place": "Galaxy Waterpark adalah taman rekreasi air yang berdiri di atas lahan seluas 2,5 hektar ini sangat cukup untuk menampung pengunjung di waktu liburan. Dengan lokasi yang sangat luas banyak wahana dan fasilitas yang bisa kamu nikmati. Di tempat ini juga tersedia beberapa kolam, dari kolam anak sampai kolam dewasa. Selain itu fasilitas yang disediakan pengelolapun juga terbilang lengkap untuk mendukung kenyamananmu saat berlibur.",
            "coordinate": {
                "lat": -7.815963299999999,
                "lng": 110.4136917
            },
            "url_image": "https://storage.googleapis.com/picture-bucket19/203.jpg"
        },
        {
            "name_place": "The Lost World Castle",
            "ticket_place": 30000,
            "rating_place": 4.3,
            "desc_place": "The Lost World Castle merupakan salah satu objek wisata di kawasan lereng Gunung Merapi yang terletak di Dusun Petung, Desa Kepuharjo Cangkringan, Sleman, Daerah Istimewa Yogyakarta, Indonesia. Objek wisata ini dibangun menyerupai Benteng Takeshi dan dibangun di atas lahan 1,3 hektare pada tahun 2016.Pengunjung yang ingin memasuki kawasan The Lost World Castle akan dikenakan biaya sebesar Rp 25.000 tiap orang dan belum termasuk biaya parkir. Biaya parkir mobil sebesar Rp5.000,00, sementara biaya parkir untuk sepeda motor sebesar Rp2.000,00.",
            "coordinate": {
                "lat": -7.6041648,
                "lng": 110.4510042
            },
            "url_image": "https://storage.googleapis.com/picture-bucket19/114.jpg"
        },
        {
            "name_place": "Bukit Bintang Yogyakarta",
            "ticket_place": 25000,
            "rating_place": 4.5,
            "desc_place": "Bukit Bintang merupakan salah satu lokasi nongkrong favorit di Yogyakarta. Saat malam tiba, pemandangan Yogyakarta sangatlah indah!Terletak di perbatasan Bantul dan Gunungkidul, siapa pun yang berkunjung ke kawasan ini dapat menikmati taburan gemintang di langit malam serta kerlip benderang lampu kota dari ketinggian.Kota ini memiliki Bukit Bintang yang selalu ramai dipadati kawula muda untuk menikmati keindahan malam. Bukit Bintang menjadi tempat yang sempurna untuk menikmati senja hingga malam tiba.",
            "coordinate": {
                "lat": -7.845840699999997,
                "lng": 110.4798457
            },
            "url_image": "https://storage.googleapis.com/picture-bucket19/112.jpg"
        },
        {
            "name_place": "The World Landmarks - Merapi Park Yogyakarta",
            "ticket_place": 22000,
            "rating_place": 4.2,
            "desc_place": "Merapi Park merupakan salah satu tempat wisata di Yogyakarta yang terletak di Jalan Kaliurang km 22, Hargobinangun, Kecamatan Pakem, Kabupaten Sleman. Pengoperasian Merapi Park dimulai sejak tanggal 25 Juni 2017. Fasilitas yang tersedia meliputi tempat pengambilan foto",
            "coordinate": {
                "lat": -7.620904599999999,
                "lng": 110.4216275
            },
            "url_image": "https://storage.googleapis.com/picture-bucket19/117.jpg"
        },
        {
            "name_place": "Sindu Kusuma Edupark (SKE)",
            "ticket_place": 20000,
            "rating_place": 4.2,
            "desc_place": "Sindu Kusuma Edupark (SKE) merupakan sebuah destinasi rekreasi yang terletak di Jogjakarta. Di tujuh hektar kawasan taman yang selesai dibangun tahun 2014 ini, terdapat bermacam-macam wahana, baik wahana bermain maupun wahana belajar yang bisa dinikmati oleh semua anggota keluarga. Wahana yang paling terkenal dari SKE adalah bianglalanya, yang merupakan sebuah wahana bermain sekaligus ikon SKE sendiri. Bila Singapore memiliki Flyer setinggi 165 meter, sementara Malaysia punya Eye setinggi 88 meter, dan di Thailand ada Asiatique setinggi 60 meter, Indonesia memiliki Cakra Manggilingan dengan tinggi 48 meter. Di tingkat Asia Tenggara, bianglala ini memang mesti berpuas diri duduk di peringkat keenam sebagai bianglala tertinggi, tetapi di tingkat nasional, ia duduk di peringkat pertama.",
            "coordinate": {
                "lat": -7.767297300000001,
                "lng": 110.3542486
            },
            "url_image": "https://storage.googleapis.com/picture-bucket19/87.jpg"
        },
        {
            "name_place": "Jogja Exotarium",
            "ticket_place": 20000,
            "rating_place": 4.4,
            "desc_place": "Di Yogyakarta, tepatnya di Sleman, ada satu tempat wisata edukasi yang patut dikunjungi. Namanya adalah Jogja Exotarium — terdengar unik, kan? Namun sebenarnya, tempat ini merupakan taman hewan berskala kecil. Koleksi hewannya beragam dan bisa diajak berinteraksi secara langsung",
            "coordinate": {
                "lat": -7.7280356,
                "lng": 110.3591712
            },
            "url_image": "https://storage.googleapis.com/picture-bucket19/138.jpg"
        },
        {
            "name_place": "Taman Pelangi Yogyakarta",
            "ticket_place": 15000,
            "rating_place": 4.3,
            "desc_place": "Taman Pelangi Yogyakarta merupakan tempat wisata malam yang menampilkan warna-warni lampu lampion, sehingga terlihat seperti pelangi. Taman wisata ini terletak di Jalan Padjajaran (sebelumnya bernama Jalan Ring Road Utara), dan berada di lokasi Museum Monumen Yogya Kembali (Monumen Jogja Kembali) Yogyakarta. Taman Pelangi memiliki lebih dari 20 jenis permainan, 25 stand makan dan stand minum. Taman pelangi ini bisa dinikmati dari sore sampai malam, atau dari jam 17.00 sampai jam 23.00. Malam hari anda akan terasa lengkap dan sempurna di Taman Pelangi dengan banyaknya lampion yang menyala memberikan kesan yang menarik untuk menenangkan pikiran.",
            "coordinate": {
                "lat": -7.7505259,
                "lng": 110.3687049
            },
            "url_image": "https://storage.googleapis.com/picture-bucket19/98.jpg"
        },
        {
            "name_place": "Heha Sky View",
            "ticket_place": 15000,
            "rating_place": 4.4,
            "desc_place": "HeHa Sky View adalah salah satu tempat wisata terbaru di Yogyakarta dan paling hits saat ini. Mau foto estetik ala selebgram atau berfoto di atas rooftop berlantai kaca? Bisa! .Terletak di Yogyakarta yang romantis, Heha Sky View adalah tempat wisata yang punya tiga ciri khas. Berdasarkan akun Instagram milik mereka, tiga hal yang menjadi ciri utama Heha Sky View adalah selfie, resto, dan food stalls. Ada banyak penjual makanan kekinian berkonsep kontainer untuk kamu yang suka memanjakan lidah. Dan kalau ingin menambah stok swafoto ada banyak spot Instagrammable yang patut dicoba.",
            "coordinate": {
                "lat": -7.8496144,
                "lng": 110.478324
            },
            "url_image": "https://storage.googleapis.com/picture-bucket19/207.jpg"
        },
        {
            "name_place": "Blue Lagoon Jogja",
            "ticket_place": 10000,
            "rating_place": 4.3,
            "desc_place": "Blue Lagoon adalah salah satu wisata air Jogja yang sudah sangat terkenal dan banyak dikunjungi oleh wisatawan dari berbagai daerah penjuru. Banyak yang menganggap bahwa Blue Lagoon sebagai surga wisata Jogja tersembunyi. Meskipun keindahan yang ditawarkan oleh tempat ini sangat mahal, namun untuk bisa menikmatinya kamu tidak perlu merogoh kocek dalam2. Karena budget untuk masuk ke area wisata ini tidak semahal yang ada di Islandia.",
            "coordinate": {
                "lat": -7.7044358,
                "lng": 110.45026
            },
            "url_image": "https://storage.googleapis.com/picture-bucket19/127.jpg"
        }
    ]
}
```
Error Response:
```
{
    "detail": "Invalid category or city."
}
```

## **4. Dependencies

FastAPI: Web framework for building APIs.
Uvicorn: ASGI server for running FastAPI.
TensorFlow: For loading and running pre-trained models.
Pandas: Data manipulation and analysis.
NumPy: Numerical operations.
Scikit-Learn: Data preprocessing tools.
