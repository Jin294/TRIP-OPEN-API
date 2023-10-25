from pymongo import MongoClient
import time


mongodb_URI = "mongodb+srv://S09P31B205:z5HxUpl4gB@ssafy.ngivl.mongodb.net/S09B31B205?authSource=admin"
client = MongoClient(mongodb_URI)
db = client["S09P31B205"]
collection = db["wiki_HTML"]
time.sleep(1)
docs = {
    "hi":"hi"
}

res = collection.insert_one(docs)
client.close()

