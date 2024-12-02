#install

pip install fastapi uvicorn

#run server
uvicorn api:app --reload atau jika gagal : python -m uvicorn api:app --reload

#Test API (jalankan di terminal) tampilannya ga seperti tampilan json karna termianl gabisa membuat tampilan json. Jadi kalo mau coba ngelihat tampilannya versi json coba buka di browser link berikut http://127.0.0.1:8000/docs

#inputkan di terminal untuk test output nya
curl -X POST "http://127.0.0.1:8000/recommendations/" \
-H "Content-Type: application/json" \
-d '{
"category": "Bahari",
"city": "Yogyakarta",
"ticket_price": 5000
}' | python -c "import sys, json; print(json.dumps(json.load(sys.stdin), indent=4))"
