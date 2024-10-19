#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Feb  2 21:54:45 2024

@author: markpowers_snhu
"""

from pymongo import MongoClient
from bson.objectid import ObjectId

class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self, USER, PASS):
        # Initializing the MongoClient. This helps to 
        # access the MongoDB databases and collections.
        # This is hard-wired to use the aac database, the 
        # animals collection, and the aac user.
        # Definitions of the connection string variables are
        # unique to the individual Apporto environment.
        #
        # You must edit the connection variables below to reflect
        # your own instance of MongoDB!
        #
        # Connection Variables
        #
        #USER = 'aacUser'
        #PASS = 'SNHU1234'
        HOST = 'nv-desktop-services.apporto.com'
        PORT = 31487
        DB = 'AAC'
        COL = 'animals'
        #
        # Initialize Connection
        #
        self.client = MongoClient('mongodb://%s:%s@%s:%d' % (USER,PASS,HOST,PORT))
        self.database = self.client['%s' % (DB)]
        self.collection = self.database['%s' % (COL)]
        print ("Connection Successful")

# Complete this create method to implement the C in CRUD.
    def create(self, data):
        if data is not None:
            create_result = self.database.animals.insert_one(data)  # data should be dictionary            
            print(create_result.inserted_id)
            return True
        else:
            return False
            raise Exception("Nothing to save, because data parameter is empty")

# Create method to implement the R in CRUD.
    def read(self, data):
        if data is not None:
            read_result = self.database.animals.find(data)
            return read_result
            
        else:
            raise Exception("Nothing to read, because data parameter is empty")
            
# Create method to implement the U in CRUD.
    def update(self, data, dataChange):
        if data is not None:
            return self.database.animals.update_many(data, {"$set": dataChange})
        else:
            raise Exception("Nothing to update, because data parameter is empty")

# Create method to implement the D in CRUD.
    def delete(self, data):
        if data is not None:
            return self.database.animals.delete_many(data)
        else:
            raise Exception("Nothing to delete, because data parameter is empty")

