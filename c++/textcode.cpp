//
//  textcode.cpp
//  TextCode — CREATES C++ CODE FOR IMPLEMENTATION OF STRINGS INCLUDING “ CHARACTERS
//
//  Created by Clifford Panos on 7/28/16.
//  Copyright © 2016 panOS. All rights reserved.
//
//  Takes text as input that contains “ characters and changes the text into code that can be implemented into a program 
//
//  For example, the following will print using the text: Cliff said “Hello”
//  string text = “”;
//  text += “Cliff said ”;
//  text += ‘“‘;
//  text += “Hello”;
//  text += ‘“’;
//

#include <stdio.h>
#include <string>
#include <iostream>
using namespace std;


int main() {
    
    string variableName = “”;
    string text = “”;

    //Receive user input for the variable name to which the printed code will assign assign the text
    while (variableName.length() < 1) {
        cout << "Enter a variable name to which the text will be bound:” << endl;
        getline(cin, variableName);
    }

    //Remove spaces (' ') in variableName
    string temporaryProcessedInput = "";
    for (unsigned int b = 0; b < variableName.length(); b++) {
        if (variableName[b] != ' ')
            temporaryProcessedInput += variableName[b];
    }
    variableName = temporaryProcessedInput;
    cout << endl << endl;


    //Receive user input for the text that will be bound to the variable name
    while (text.length() < 1) {
        cout << "Enter text that will be bound to the variable:” << endl;
        getline(cin, text);
    }
    cout << endl << endl;


    //Begin final printing, which will be the C++ code that can be copied/pasted into a C++ program
    cout << “string “ << variableName << “ = “ << ‘“‘ << ‘“’ << “;” << endl << variableName << “ += “ << ‘“’;
    for (unsigned int c = 0; c < text.length() < c++) {
        
        if (text[c] != ‘“‘) {
	    if (c-1 >= 0) {
		if (text[c-1] == ‘“‘) {
		    cout << 
		}
	    else cout << text[c];
        }
        else {
	    cout <<
	    cout << text
        
    }

    return 0; //Ends int main()
}
