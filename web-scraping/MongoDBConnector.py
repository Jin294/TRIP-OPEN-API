import pymongo
import time
import asyncio

class MongoDBConnector:
    def __init__(self):
        try: 
            self.client = pymongo.MongoClient("mongodb+srv://S09P31B205:z5HxUpl4gB@ssafy.ngivl.mongodb.net/S09B31B205?authSource=admin")
            time.sleep(3)
            print(f"connect check : {self.client}")
            self.db = self.client["S09P31B205"]
            self.collection = self.db["wiki_HTML"]
        except pymongo.errors.ConnectionFailure as e:
            print(f"Failed to connect to MongoDB: {e}")

    def insert_data(self, data):
        # 데이터를 MongoDB에 삽입
        result = self.collection.insert_one(data)
        return result.inserted_id
    
    
