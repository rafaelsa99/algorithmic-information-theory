# G7
Group 7

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

## Lab Work 2

### How to run:
To run the program simply run the commands below in the bin directory.


### Requirements:
Java Runtime Environment <br>
Git LFS

#### Steps in Ubuntu:
1 - Install git-lfs - "apt install git-lfs" <br>
2 - When the process finishes, execute the following command - "git lfs install system" <br>
3 - Then clone the project "git lfs clone https://github.com/DETI-TAI/G7.git". If you already have done it, just do "git lfs pull". You must have to enter your credentials from github twice. <br>


### Parameters:
- <b>orderK</b>: Order of the model (k > 0)<br>
- <b>alpha</b>: Smoothing parameter (0 >= a <= 1)<br>
- <b>LanguagesFiles</b>: Path to file with the various languages to use as references. The file must obey to the following structure: <lang x>,<Path to File with x lang>
- <b>targetText</b>: This is the path to the file we want to classify (target).
- <b>[fulltext OR segments]</b>: This defines if the program will evaluate the text by the complete file or by segments.
  
  ...
(Optional for segments):
  - <b>lowPassFilterSmoothing</b>: The smoothing parameter for the low pass filter we want to apply in the classification by segments. By default, the value is 40.
  - <b>minSegmentSize</b>: The minimum length of a segment in the classification by segments. By default, the value is 5.
  
<b>Note: </b> 
- This type of execution should be used when several files are to be used for references.
- The program only contains data for execution with the dataset, so to test with the existing data the parameters below must be used.  
  
#### OR:

- <b>orderK</b>: Order of the model (k > 0)<br>
- <b>alpha</b>: Smoothing parameter (0 >= a <= 1)<br>
- <b>--dataset</b>: This parameter will enable to load the dataset for training<br>
- <b>targetText</b>: This is the path to the file we want to classify (target).
- <b>textFile</b>: Path to the dataset references text file for training. The file must have a reference paragraph for a language for each line.
- <b>languageFile</b>: Path to the dataset labeling file. This file identifies the language in which each line of the dataset references text file is written.
- <b>[fulltext OR segments]</b>: This defines if the program will evaluate the text by the complete file or by segments.

  ...
(Optional for segments):
  - <b>lowPassFilterSmoothing</b>: The smoothing parameter for the low pass filter we want to apply in the classification by segments. By default, the value is 40.
  - <b>minSegmentSize</b>: The minimum length of a segment in the classification by segments. By default, the value is 5.


#### Run language:
```
java -jar <jar_file> orderK alpha referenceText targetText
```

#### Run languageRecognition:
```
java -jar <jar_file> orderK alpha LanguagesFiles targetText [fulltext OR segments] lowPassFilterSmoothing minSegmentSize
```
##### OR

#### Run languageRecognition:
```
java -jar <jar_file> orderK alpha --dataset targetText textFile languageFile [fullText OR segments]
```

#### Run precision:
```
java -jar <jar_file> orderK alpha targetTextsFile textFile languageFile
```

#### language Example:
```
java -jar language.jar 3 0.001 ../textFiles/example.txt ../textFiles/target.txt
```
##### Expected results for the example:
```
Reference Model build time: 1.2577929 seconds
Entropy of Reference Text: 1.96147900668802
Number of bits needed to compress target: 14541.590990558034 bits
```

#### languageRecognition with segments Example:
```
java -jar languageRecognition.jar 3 0.001 --dataset ../textFiles/target.txt ../textFiles/references.txt ../textFiles/langReferences.txt segments 40 5
```
##### Expected results for the example above with [segments]:
```
Reference Model build time: 34.5594236 seconds
Language: Portuguese ; Start: 0 ; End: 73
Language: English ; Start: 74 ; End: 81
Language: Portuguese ; Start: 82 ; End: 127
Language: English ; Start: 128 ; End: 174
Language: Portuguese ; Start: 175 ; End: 546
Language: French ; Start: 547 ; End: 834
Language: English ; Start: 835 ; End: 1331
Language: Kinyarwanda ; Start: 1332 ; End: 1337
Language: French ; Start: 1338 ; End: 1378
Language: Kinyarwanda ; Start: 1379 ; End: 1386
Language: Portuguese ; Start: 1387 ; End: 1824
Language: German ; Start: 1825 ; End: 2156
```

#### languageRecognition with fulltext Example:
```
java -jar languageRecognition.jar 3 0.001 --dataset ../textFiles/target.txt ../textFiles/references.txt ../textFiles/langReferences.txt fulltext
```
##### Expected results for the example above with [fulltext]:
```
Reference Model build time: 37.306744 seconds
Target file: ../textFiles/target.txt is in Portuguese language, with compression of 9955.854953129723 bits
```

#### precision Example:
```
java -jar precision.jar 1 0.1 ../textFiles/longTargetFiles_bin.txt ../textFiles/references.txt ../textFiles/langReferences.txt
```

##### Expected results for the example:
```
Classification Accuracy: 90.19607843137256%
Languages with wrong classifications:
        Expected language: Oriya -> Obtained language: Min Nan Chinese
        Expected language: Bosnian -> Obtained language: Croatian
        Expected language: Bhojpuri -> Obtained language: Hindi
        Expected language: Indonesian -> Obtained language: Malay
        Expected language: Norwegian Nynorsk -> Obtained language: Norwegian Bokmal
        Expected language: Cebuano -> Obtained language: Tagalog
        Expected language: Egyptian Arabic -> Obtained language: Arabic
        Expected language: Serbo-Croatian -> Obtained language: Serbian
        Expected language: Hakka Chinese -> Obtained language: Cantonese
        Expected language: Jamaican Patois -> Obtained language: English
```
