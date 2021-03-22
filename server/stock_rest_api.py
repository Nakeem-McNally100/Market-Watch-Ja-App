from flask import Flask, request, jsonify
from flask_restful import Api, Resource
import database as dbmanager

app=Flask(__name__)
api=Api(app)
companies={"app":{"name":"apple","stock":123}}
cnewz={"app":{"name":"apple","health":'good'}}

class User(Resource):
    def get(self):
        accountID=request.form["acctid"]
        return dbmanager.getSalaryByAccountID(accountID)

        
    def post(self):
        fname=request.form["fname"]
        lname=request.form["lname"]
        acctid=request.form["acctid"]
        pword=request.form["pword"]
        uname=request.form["uname"]
        print(fname,lname,acctid,pword,uname+"")
        dbmanager.insertemployeeAccount(fname,lname,acctid,pword,uname)

class Company1(Resource):
    def get(self,company):
        acctid=request.form["acctid"]
        return companies[company]

class News(Resource):
    def get(self,company):
        return cnews[company]

api.add_resource(User, "/user")
api.add_resource(News, "/news/<string:company>")
api.add_resource(Company1, "/company/<string:company>")




if __name__=='__main__':
    app.run(debug=True)
