from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import difflib

def textSimilarity(fromStr, toStr):
    sentences = (fromStr, toStr)

    answer_bytes = bytes(fromStr, 'utf-8')
    input_bytes = bytes(toStr, 'utf-8')
    answer_bytes_list = list(answer_bytes)
    input_bytes_list = list(input_bytes)

    sm = difflib.SequenceMatcher(None, answer_bytes_list, input_bytes_list)
    similar = sm.ratio()
    return similar

def cos(fromStr, toStr):
    sentences = (fromStr, toStr)

    tfidf_vectorizer = TfidfVectorizer()
    tfidf_matrix = tfidf_vectorizer.fit_transform(sentences)
    cos_similar = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:2])
    return cos_similar[0][0]

def jak(answer_string, input_string):
    intersection_cardinality = len(set.intersection(*[set(answer_string), set(input_string)]))
    union_cardinality = len(set.union(*[set(answer_string), set(input_string)]))
    similar = intersection_cardinality / float(union_cardinality)
    return similar

# print(textSimilarity("국립 청태산자연휴양림", "청태산"))
# print(cos("국립 청태산자연휴양림", "청태산"))
# print(jak("국립 청태산자연휴양림", "청태산"))

print(textSimilarity("해운대해수욕장", "해운대"))
