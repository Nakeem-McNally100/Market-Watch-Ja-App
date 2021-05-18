import math
import os
os.environ["CUDA_VISIBLE_DEVICES"] = "-1"
from datetime import datetime
import pandas_datareader as web
import pandas as pd
import numpy as np
import re
import csv
import tensorflow as tf
import time
from sklearn.preprocessing import  MinMaxScaler, StandardScaler
from keras.models import Sequential
from keras.layers import Dense, LSTM, Dropout
import matplotlib.pyplot as plt
import numpy
plt.style.use('fivethirtyeight')


def load_data(ticker):
    tesladf = web.DataReader(ticker,data_source='yahoo', start='2012-01-01', end = '2021-05-04')
    tesladf = tesladf.reset_index()
    relative_close=[]
    for price in range (0,tesladf['Open'].count()):
        relative_close.append(tesladf['Close'][price]/tesladf['Open'][price])
    tesladf.insert(5,"relative",relative_close,True)
    return tesladf

def clean_data(ticker):
    nfuture = 1
    npast = 60
    x_predict = []
    y_predict = []
    tesladf=load_data(ticker)
    tesladataset = tesladf.filter(['High', 'Low','Open','Close','relative','Volume'])
    days30=past30days(tesladataset)
    cols = list(tesladataset)[:]
    tesladataset = tesladataset[cols].astype(float)
    tesladataset.head(3)
    predict_len=int(len(tesladataset)-60)
    predict_set=tesladataset.iloc[0:predict_len,:]
    scalar = StandardScaler() # feature range not set?Why?
    scalar = scalar.fit(predict_set)
    predict_set=scalar.transform(predict_set)
    predict_set =predict_set[-61:]
    previous_data=scalar.inverse_transform(predict_set)[-31:,3]
    #print(predict_set)
    for i in range(npast,predict_set.shape[0]-nfuture+1):
        x_predict.append(predict_set[i-npast:i,0:predict_set.shape[1]])
        y_predict.append(predict_set[i+nfuture-1:i+nfuture,3])
    
    x_predict, y_predict = np.array(x_predict), np.array(y_predict)
    x_predict.shape , y_predict.shape

    new_model = tf.keras.models.load_model('saved_model/my_model')
    predictions = new_model.predict(x_predict)
    print("load")
    predictions.shape , y_predict.shape
    final_prediction=[]
    for itter in range(0,7):
        predictions = new_model.predict(x_predict)
        predictions.shape , y_predict.shape
        predictions_copies = np.repeat(predictions, predict_set.shape[-1], axis=-1)
        predictions_future = scalar.inverse_transform(predictions_copies)[:,0]
        #print(x_predict)
        new_prediction=x_predict[0][1:]
        #print(new_prediction)
        #new_prediction= numpy.concatenate((new_prediction[0],predictions_copies[0]))
        new_prediction=numpy.insert(new_prediction,len(new_prediction),predictions_copies[0],axis=0)
        x_predict=np.array([new_prediction])
        #x_predict=numpy.concatenate((x_predict,predictions_copies))
        #x_predict=numpy.concatenate((x_predict,predictions_copies[1]))
        #x_predict.append(predictions_copies[0])
        #x_predict.append(predictions_copies[1])
        
        final_prediction.append( predictions_future[0])
        
        
        #x_predict= np.reshape(1, x_predict,(x_predict.shape[0], x_predict.shape[1]))
        
        #x_predict.shape ,predictions_copies
    final_prediction.append(days30.tolist())
    final_prediction.append(previous_data.tolist())
    
    print(final_prediction)
    
    return final_prediction

def past30days(tesladataset):
    cols = list(tesladataset)[:]
    tesladataset = tesladataset[cols].astype(float)
    training_lenght = int(len(tesladataset))
    training_lenght
    train_set = tesladataset.iloc[0:training_lenght,:]
    len(train_set)
    scalar = StandardScaler() # feature range not set?Why?
    scalar = scalar.fit(train_set)
    train_set = scalar.transform(train_set)
    train_set=train_set[-91:]    
    nfuture = 1
    npast = 60
    x_train = []
    y_train = []
    for i in range(npast,train_set.shape[0]-nfuture+1):
        x_train.append(train_set[i-npast:i,0:train_set.shape[1]])
        y_train.append(train_set[i+nfuture-1:i+nfuture,3])

    x_train, y_train = np.array(x_train), np.array(y_train)
    x_train.shape , y_train.shape
    #print (x_train)
    new_model = tf.keras.models.load_model('saved_model/my_model')
    predictions = new_model.predict(x_train)
    predictions_copies = np.repeat(predictions, train_set.shape[1], axis=-1)
    predictions_future = scalar.inverse_transform(predictions_copies)[:,0]
    #print(predictions_future)
    return predictions_future
#if __name__=='__main__':

    #clean_data("PG")
