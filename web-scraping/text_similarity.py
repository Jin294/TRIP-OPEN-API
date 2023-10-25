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


# textSimilarity("해운대(영화)", "해운대")