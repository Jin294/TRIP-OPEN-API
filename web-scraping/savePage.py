from pymongo import MongoClient
import asyncio

async def savePage(docs):
    mongodb_URI = "mongodb+srv://S09P31B205:z5HxUpl4gB@ssafy.ngivl.mongodb.net/S09B31B205?authSource=admin"
    client = MongoClient(mongodb_URI)
    db = client["S09P31B205"]
    collection = db["wiki_HTML"]
    res = collection.insert_one(docs)
    return res

# asyncio.run(savePage({"hi":"hello"}))


