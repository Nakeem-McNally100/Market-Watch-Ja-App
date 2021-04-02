from flask import Flask, request, jsonify
from flask_restful import Api, Resource
import marketwatchjaDatabase as dbmanager

app=Flask(__name__)
api=Api(app)
companies={"app":{"name":"apple","stock":123}}
cnewz={"app":{"name":"apple","health":'good'}}

class User(Resource):
    def get(self,email):
        #email=request.form["email"]
        return dbmanager.getUserByEmail(email)
        
        
    def post(self):
        userid=request.form["userid"]
        email=request.form["email"]
        password=request.form["password"]
        fullname=request.form["fullname"]
        
        print(userid,email,password,fullname+"")
        dbmanager.insertUserProfile(userid,email,password,fullname)

class Company1(Resource):
    def get(self,company):
        acctid=request.form["acctid"]
        return companies[company]

class News(Resource):
    def get(self,company):
        return cnews[company]

api.add_resource(User, "/user/<string:email>")
api.add_resource(News, "/news/<string:company>")
api.add_resource(Company1, "/company/<string:company>")




if __name__=='__main__':
    app.config["DEBUG"] = True
    app.run(host = '0.0.0.0')

# Link below shows you how to make the rest api accessible on your LAN
# https://stackoverflow.com/questions/59026168/how-to-access-localhost-from-another-computer-on-same-network


