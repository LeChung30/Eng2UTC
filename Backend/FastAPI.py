from fastapi import FastAPI
import FirebaseController as fc
import uvicorn

app = FastAPI()

@app.get("/topic/all")
def get_all_topics():
    res=fc.get_alltopic()
    return res

@app.get("/topic/{topic_id}")
def get_topic(topic_id: int):
    res=fc.get_topic(topic_id)
    return res

@app.get("/question/{test_id}")
def get_question(test_id: int):
    res=fc.get_question_by_test_id(test_id)
    return res
#Lấy part_detail,question, answer theo test_id
@app.get("/data/{test_id}")
def get_data(test_id: str):
    res = fc.get_data_by_test_id(test_id)
    if res == -1:
        return {"error": "An error occurred while fetching data"}
    return res

if __name__ == '__main__':
    uvicorn.run(app,port=8000)
