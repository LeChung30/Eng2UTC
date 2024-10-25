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

if __name__ == '__main__':
    uvicorn.run(app,port=8000)
