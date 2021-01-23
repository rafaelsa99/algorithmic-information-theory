# Lab Work 1
The objective of the program is to create a model with the purpose of collecting statistical information about texts, using finite-context models. <br>
A finite-context (Markov) model, of order k, produces the probability distribution of the next symbol in a sequence of symbols, considering the recent history up to the depth k.

# Elements

Nome: António Jorge Ferreira Ramos

GitHub: tonyevolutionstar

Email: ajframos@ua.pt

Nº: 101193

<br>

Nome: Luís Carlos Laranjeira Marques

GitHub: LuiisLaranjeira

Email: lclm@ua.pt

Nº: 81526

<br>

Nome: Rafael Gomes de Sá

GitHub: rafaelsa99

Email: rafael.sa@ua.pt

Nº: 104552

## Lab Work 1

### How to run:
To run the program simply run the commands below in the bin directory.


### Requisites:
Java Runtime Environment


### Parameters:
- <b>orderK</b>: Order of the model (k > 0)<br>
- <b>alpha</b>: Smoothing parameter (0 >= a <= 1)<br>
- <b>filePath</b>: Path of the text file<br>
- <b>StartingText</b>: Prior to the text to be generated<br>
- <b>TextLength</b>: Length of the text to be generated<br>


#### Run fcm:
```
java -jar <jar_file> orderK alpha filePath
```

#### fcm Example:
```
java -jar fcm.jar 3 0.2 ..\example\example.txt
```

#### Run generator:
```
java -jar <jar_file> orderK alpha filePath StartingText TextLength
```

#### generator Example:
```
java -jar generator.jar 3 0.2 ..\example\example.txt "Example" 500000
```

#### Expected results for the example:
```
Model build time: 1.6060728 seconds
Entropy: 1.4611980116493142
Text generation time: 15.4837783 seconds
```
