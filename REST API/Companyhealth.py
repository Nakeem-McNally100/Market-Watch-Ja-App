#import plotly.graph_objects as graph_objects
import requests
import pandas as pd 
import csv
import json

#call extract_healthdata(ticker,amount)

def get_data_ratio(ticker,amount):
    companayinfo=requests.get(f'https://financialmodelingprep.com/api/v3/ratios/{ticker}?period=quarter&limit={amount}&apikey=0024359f52a2b24c60b559427fe98116')
    jsoner=json.loads(companayinfo.text)
    #print(jsoner)
    return jsoner
    

def get_data_income(ticker,amount):
    companayinfo=requests.get(f'https://financialmodelingprep.com/api/v3/income-statement/{ticker}?period=quarter&limit={amount}&apikey=0024359f52a2b24c60b559427fe98116')
    jsoner=json.loads(companayinfo.text)
    #print(jsoner)
    return jsoner

def get_data_balance(ticker,amount):
    companayinfo=requests.get(f'https://financialmodelingprep.com/api/v3/balance-sheet-statement/{ticker}?period=quarter&limit={amount}&apikey=0024359f52a2b24c60b559427fe98116')
    jsoner=json.loads(companayinfo.text)
    #print(jsoner))
    return jsoner

def get_data_cashflow(ticker,amount):
    companayinfo=requests.get(f'https://financialmodelingprep.com/api/v3/cash-flow-statement/{ticker}?period=quarter&limit={amount}&apikey=0024359f52a2b24c60b559427fe98116')
    jsoner=json.loads(companayinfo.text)
    #print(jsoner))
    return jsoner

def extract_healthdata(ticker,amount):
    
    
    balance_sheet=get_data_balance(ticker,amount)
    report=get_data_income(ticker,amount)
    ratios=get_data_ratio(ticker,amount)
    cashflow=get_data_cashflow(ticker,amount)

    free_cashflow=cashflow[0]["freeCashFlow"]
    operating_cashflow=cashflow[0]["operatingCashFlow"]
    currentratio=ratios[0]["currentRatio"]#is an indicator for the purposes of measuring the short-term liquidity of an entity. solvency test

    netincome =report[0]["netIncome"]
    dept_repayment=cashflow[0]["debtRepayment"]

    current_assets1=balance_sheet[0]["totalCurrentAssets"]#asset the company will recieve in commnig year
    current_liab1=balance_sheet[0]["totalCurrentLiabilities"]#obligations the company has to pay within the coming year
    inventory=balance_sheet[0]["inventory"]
    acidtest=current_assets1-(inventory/current_assets1)#the ability of paying debts and obligations when due

    noncurrent_assets1=balance_sheet[0]["totalNonCurrentAssets"]# will not recieve within the year
    noncurrent_liab1=balance_sheet[0]["totalNonCurrentLiabilities"]# will not pay withing the year

    assetsturnover=ratios[0]["assetTurnover"]#how efficiently a company is using its assets
    grossmargin=ratios[0]["grossProfitMargin"]#A company with high profitability obtains a 60% margin or more.
    debtratio=ratios[0]["debtRatio"]

    try:
        debtcoverage=netincome/dept_repayment
    except:
        debtcoverage=0
        
    working_capital1=current_assets1-current_liab1

    healthDataList = [free_cashflow,operating_cashflow,currentratio,current_assets1
                      ,current_liab1,noncurrent_assets1,noncurrent_liab1,
                      acidtest,assetsturnover,grossmargin,debtratio,debtcoverage
                      ,working_capital1]
    
    json_data={"free cashflow":f"{free_cashflow}",
                "operating cashflow":f"{operating_cashflow}",
                "current ratio":f"{currentratio}",
                "current assets":f"{current_assets1}",
                "current liailities":f"{current_liab1}",
                "non current assets":f"{noncurrent_assets1}",
                "non current liailities":f"{noncurrent_liab1}",
                "acid test":f"{acidtest}",
                "asset turnover":f"{assetsturnover}",
                "grossmargin":f"{grossmargin}",
                "debt ratio":f"{debtratio}",
                "debt coverage":f"{debtcoverage}",
                "working capital":f"{working_capital1}"}
    jsoner=json.dumps(json_data)
    #return(jsoner)
    return healthDataList








#if __name__ == "__main__":
    #extract_healthdata("AAPL","1")

#companayinfo=requests.get('https://financialmodelingprep.com/api/v3/quote/AAPL?apikey=0024359f52a2b24c60b559427fe98116')
#print(companayinfo.text)


