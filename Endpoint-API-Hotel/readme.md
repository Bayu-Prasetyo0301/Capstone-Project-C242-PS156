#install
pip install fastapi uvicorn

#run
uvicorn api:app --reload



#test
curl -X POST "http://127.0.0.1:8000/recommendations/" \
-H "Content-Type: application/json" \
-d '{
    "location": "Bandung",
    "budget": 2000000
}'
