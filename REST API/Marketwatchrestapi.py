from flask import Flask, request, jsonify
from flask_restful import Api, Resource
import Stockmarketdatabase as dbmanager
import SentimentAnalysis as analysis
import Companyhealth as health
import random
import json
import Predictionparser as parser

import prediction as predictor


app=Flask(__name__)
api=Api(app)
companies={"app":{"name":"apple","stock":123}}
cnewz={"app":{"name":"apple","health":'good'}}

examplepredictiondata = [128.71623, 128.57799, 128.77603, 129.33626, 130.38635, 132.01085, 134.21452,
                         [128.22418212890625, 128.53614807128906, 128.96945190429688, 129.53887939453125,
                          130.1899871826172, 130.97927856445312, 131.9606475830078, 133.00732421875, 134.02059936523438,
                          134.9370574951172, 135.81387329101562, 136.56809997558594, 137.2023162841797, 137.69808959960938, 138.03536987304688,
                          138.23866271972656, 138.26976013183594, 138.17286682128906, 138.0367431640625, 137.85643005371094, 137.61236572265625,
                          137.4772186279297, 137.39862060546875, 137.25315856933594, 137.01708984375, 136.61001586914062, 136.0522918701172,
                          135.39524841308594, 134.75564575195312, 134.19273376464844, 133.73220825195312],
                         [136.5500030517578, 136.33999633789062,137.72000122070312, 138.67999267578125, 138.4199981689453, 137.77000427246094, 139.13999938964844, 137.82000732421875,
                    138.6999969482422, 140.16000366210938, 138.85000610351562, 138.7899932861328, 137.85000610351562, 137.0500030517578,
                137.25999450683594, 135.8000030517578, 134.77999877929688, 133.60000610351562, 131.92999267578125, 131.00999450683594,
                130.0, 132.24000549316406, 133.08999633789062, 128.3800048828125, 130.36000061035156, 128.2100067138672, 128.97000122070312,
                 128.7899932861328, 128.9499969482422, 129.02999877929688, 129.57000732421875]]







def updateSystemDataset(): 
    mycompanies = dbmanager.getCompanies()
    for company in mycompanies:
        ticker = company[1]
        name = company[0]
        sentiment = analysis.pullTweets(name)
        dbmanager.insertSentimentResults(name,sentiment[0],sentiment[1],
                sentiment[2], sentiment[5], sentiment[4], sentiment[3])

        healthdata = health.extract_healthdata(ticker,'1')

        dbmanager.insertHealtResults(name,healthdata[0],healthdata[1],
        healthdata[2],healthdata[3],healthdata[4],healthdata[5],healthdata[6],
                healthdata[7],healthdata[8],healthdata[9],healthdata[10],
                                     healthdata[11],healthdata[12])
        


    

class User(Resource):
    def get(self,email,userid,password,fullname):
        return dbmanager.getUserProfile(email)
    
    def post(self,userid, email,password,fullname):
        dbmanager.insertUser(userid,email,password,fullname)
        return dbmanager.getUserProfile(email)



class CompanyData(Resource):
    
    def get(self,company):
        if "-" in company:
            company = company.replace("-"," ")
            
        companysentiment = dbmanager.getSentimentResults(company)
        companyhealth = dbmanager.getHealthResults(company)
        ticker = dbmanager.getTicker(company)
        predictdata = predictor.clean_data(ticker)

        #predict = parser.prepPredictions(examplepredictiondata) #dummy data test
        predict = parser.prepPredictions(predictdata)
        

        data=[{
                    "positive":companysentiment[2],
                    "negative": companysentiment[3],
                    "neutral" : companysentiment[4],
                    "subjective":companysentiment[5],
                    "objective": companysentiment[6],
                    "Totaltweets":companysentiment[7]
                    },
                    {
                "free cashflow":companyhealth[2],
                "operating cashflow":companyhealth[3],
                "current ratio":companyhealth[4],
                "current assets":companyhealth[5],
                "current liailities":companyhealth[6],
                "non current assets":companyhealth[7],
                "non current liailities":companyhealth[8],
                "acid test":companyhealth[9],
                "asset turnover":companyhealth[10],
                "grossmargin":companyhealth[11],
                "debt ratio":companyhealth[12],
                "debt coverage":companyhealth[13],
                "working capital":companyhealth[14]
                },
                {
                    "sevendayprediction": predict[0],
                    "thirtydayprediction": predict[1],
                    "thirtydayactual": predict[2]

                }
                    ]

    

        return jsonify({"companydata": data})



api.add_resource(User, "/user/<string:email>/<string:userid>/<string:password>/<string:fullname>")
api.add_resource(CompanyData, "/companyresult/<string:company>")

if __name__=='__main__':    
    app.config["DEBUG"] = True
    app.run(host = '0.0.0.0')



