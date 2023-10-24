from pymongo import MongoClient
import time


mongodb_URI = "mongodb+srv://S09P31B205:z5HxUpl4gB@ssafy.ngivl.mongodb.net/S09B31B205?authSource=admin"
client = MongoClient(mongodb_URI)
db = client["S09P31B205"]
collection = db["wiki_HTML"]

docs = {
    "hi":"hi"
}

res = collection.insert_one(docs)


# def saveHTML(rootName, pageName, depth, html):
    
#     htmlInfo = {
#         "root_name":rootName,
#         "page_name":pageName,
#         "depth":depth,
#         "html":html
#     }
#     res = collection.insert_one(htmlInfo)


# saveHTML("hi", "hi", 1, "<h1>hi</h1")

client.close()

# def insert_data_into_mongodb(data):
#     # MongoDB 연결 정보
#     mongodb_URI = "mongodb+srv://S09P31B205:z5HxUpl4gB@ssafy.ngivl.mongodb.net/S09B31B205?authSource=admin"
    
#     # MongoDB 클라이언트 초기화
#     client = MongoClient(mongodb_URI)
    
#     # 데이터베이스와 컬렉션 선택
#     db = client["S09P31B205"]
#     collection = db["wiki_HTML"]


#     # 데이터 삽입
#     collection.insert_one(data)

# # 삽입할 데이터
# data_to_insert = {
#     "hi": "hihi"
# }

# # 함수 호출로 데이터 삽입
# insert_data_into_mongodb(data_to_insert)