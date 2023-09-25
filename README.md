# CoreNLP-Learning
# 环境配置
## 下载框架文件和语言model依赖。

框架文件中，包含CoreNLP的以下内容：
(1) CoreNLP jar，(2) CoreNLP 模型 jar（大多数任务的类路径中都需要）(3) 运行 CoreNLP 所需的库，以及 (4) 项目的文档/源代码。

![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/aafee7c2-1f31-4f26-8846-68ef129f86c1)


在CoreNLP中，English KBP和English Extra是两个有关英文语言处理的功能模块。

English KBP（Knowledge Base Population）是用于知识库填充的功能模块。它利用自然语言处理技术从大量文本数据中提取实体、关系和事件等信息，并将其注入到知识库中，以便后续的信息检索和知识推理等任务。

English Extra是一组额外的英文语言处理功能，包括词性标注（Part-of-Speech Tagging）、命名实体识别（Named Entity Recognition）、依存句法分析（Dependency Parsing）等。这些功能可以帮助解析和理解英文文本的结构和语义，从而支持更高级的文本分析任务，比如信息抽取、问答系统等。

如果需要对中文处理，则需要下载 Chinese model jar 文件。
### 解压框架文件结果

解压后的文件目录如下：

![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/89dc7656-af5b-44f3-935e-922ea6ca45aa)


### 转移 Language-Model

将需要的 Language-Model转移到 解压后的目录下
## Java 环境配置

![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/e788ac41-506d-4688-9108-e9545b0cdcaf)


通过winget search jdk 查找java安装包，选择11版本进行安装。
使用命令winget install ojdkbuild.openjdk.11.jdk进行安装
安装成功后进行java环境配置，需要注意winget 下载后只配置了path没有配置JAVA_HOME 和 CLASSPATH
JAVA_HOME的配置如下
![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/1f7b2402-582c-454a-82e2-042281f3fa35)


CLASSPATH配置如下
![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/022e6938-17f8-4f73-a98a-b7cd2d90899a)


其中设置了 stanford-corenlp-4.5.5 的Jar文件，需要注意 `*` 表示匹配任意长度的任意字符，以此能匹配该目录下的所有文件。
```powershell
.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;D:\project\stanford-corenlp-4.5.5\stanford-corenlp-4.5.5\*
```

如果不在CLASSPATH添加coreNLP的jar地址，则需要在Java执行中，使用`-cp` 命令指向 coreNLP的jar地址，从而正常运行。

```powershell
java -cp D:\project\stanford-corenlp-4.5.5\stanford-corenlp-4.5.5\* [CLASS_NAEM] [parameters]
```

如下:
```powershell
java -cp D:\project\stanford-corenlp-4.5.5\stanford-corenlp-4.5.5\* edu.stanford.nlp.pipeline.StanfordCoreNLPServer -serverProperties StanfordCoreNLP-chinese.properties -port 9000 -timeout 15000
```
## 本地命令运行

### 文件输入
进入到project目录下，使用code命令
如下
![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/2fa62b83-c2cd-4c66-ba26-2638c6f3ebde)

此时进入vscode
需要使用new file新建输入文本文件
接着使用如下命令
![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/922d5db1-6a79-405c-9f6b-18c0ce07ded1)

如下命令 java edu.stanford.nlp.pipeline.StanfordCoreNLP -file input.txt
得到输出结果 
![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/4c881bf0-0b48-4342-884a-92df4e6e536f)


### Web 服务
使用如下命令开启服务
```Powershell
java -cp "*" edu.stanford.nlp.pipeline.StanfordCoreNLPServer -serverProperties StanfordCoreNLP-chinese.properties -port 9000 -timeout 15000
```
此命令所在地
![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/bbbe5917-0da5-4287-bc8a-5b8849ae111f)


访问链接  
127.0.0.1.9000
![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/c0d8a4f2-dc22-44d1-b229-033c36c3157e)


进入如下页面
![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/5304f362-43c6-4cf0-9319-1c4defca79e5)


可自行选择语言，注意我们在前面Language-Model添加了英文和中文，如需其他语言需要添加相应的Language-Model
## 开发与编译

Maven 环境设置
### 环境部署

参考文档:
[CoreNLP API - CoreNLP (stanfordnlp.github.io)](https://stanfordnlp.github.io/CoreNLP/api.html)

#### 新建 Maven 项目
在Maven的环境部署环节中，首先需要新建Maven项目:

![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/e03920f0-3197-4511-a0b1-85d8e2c72021)


#### 设置Maven aliyun源和全局代理
同时，为了方便Maven的下载过程，可以修改Maven仓库的全局配置文件，`~/.m2/settings.xml`

```xml
<settings>
        <mirrors>
                <mirror>
                    <id>aliyunmaven</id>
                    <mirrorOf>*</mirrorOf>
                    <name>aliyunmaven</name>
                    <url>https://maven.aliyun.com/repository/public</url>
                </mirror>
        </mirrors>
        <proxies>
                <proxy>
                        <id>clash</id>
                        <active>true</active>
                        <protocol>http</protocol>
                        <host>127.0.0.1</host>
                        <port>7890</port>
                </proxy>
        </proxies>
</settings>
```

#### 利用Maven设置项目依赖

添加 所需要的依赖：1) StanfordNLD框架；2) 语言model 依赖

确定pom.xml 文件内容后，可以选择右侧的重新加载工具进行下载依赖。

![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/34656b87-405a-4089-85f7-f562c0033e91)

pom.xml 文件是maven工具用于管理本地项目的依赖和构建的配置文件，当前具体内容如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>  
<project xmlns="http://maven.apache.org/POM/4.0.0"  
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">  
    <modelVersion>4.0.0</modelVersion>  
  
    <groupId>xyz.mango</groupId>  
    <artifactId>corenlp-demo</artifactId>  
    <version>1.0-SNAPSHOT</version>  
  
    <properties>        <maven.compiler.source>11</maven.compiler.source>  
        <maven.compiler.target>11</maven.compiler.target>  
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>  
    </properties>  
  
    <dependencies>        <dependency>            <groupId>edu.stanford.nlp</groupId>  
            <artifactId>stanford-corenlp</artifactId>  
            <version>4.5.4</version>  
        </dependency>        <dependency>            <groupId>edu.stanford.nlp</groupId>  
            <artifactId>stanford-corenlp</artifactId>  
            <version>4.5.4</version>  
            <classifier>models</classifier>  
        </dependency>        <dependency>            <groupId>edu.stanford.nlp</groupId>  
            <artifactId>stanford-corenlp</artifactId>  
            <version>4.5.4</version>  
            <classifier>models-chinese</classifier>  
        </dependency>    </dependencies>  
</project>
```

### API 学习

Referencs:
- [coreNLP-java使用（中文） - 简书 (jianshu.com)](https://www.jianshu.com/p/77c29af0c574)
- [CoreNLP API - CoreNLP (stanfordnlp.github.io)](https://stanfordnlp.github.io/CoreNLP/api.html)

#### 文本处理

```java
package xyz.mango;  
  
import edu.stanford.nlp.coref.data.CorefChain;  
import edu.stanford.nlp.ling.*;  
import edu.stanford.nlp.ie.util.*;  
import edu.stanford.nlp.pipeline.*;  
import edu.stanford.nlp.semgraph.*;  
import edu.stanford.nlp.trees.*;  
import java.util.*;  
  
public class Main {  
  
    public static String text = "Joe Smith was born in California. " +  
            "In 2017, he went to Paris, France in the summer. " +  
            "His flight left at 3:00pm on July 10th, 2017. " +  
            "After eating some escargot for the first time, Joe said, \"That was delicious!\" " +  
            "He sent a postcard to his sister Jane Smith. " +  
            "After hearing about Joe's trip, Jane decided she might go to France one day.";  
  
    public static void main(String[] args) {  
        // 设置管道属性  
        Properties props = new Properties();  
        // 设置要运行的注解器列表  
        props.setProperty("annotators", "tokenize,pos,lemma,ner,parse,depparse,coref,kbp,quote");  
        // 设置共指消解注解器使用神经算法  
        props.setProperty("coref.algorithm", "neural");  
        // 创建NLP管道  
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);  
        // 创建文档对象  
        CoreDocument document = new CoreDocument(text);  
        // 注解文档  
  
        pipeline.annotate(document);  
  
        // 示例  
  
        // 从文档中获取第10个标记  
        CoreLabel token = document.tokens().get(10);  
        System.out.println("Example: token");  
        System.out.println(token);  
        System.out.println();  
  
        // 获取第一个句子的文本  
        String sentenceText = document.sentences().get(0).text();  
        System.out.println("Example: sentence");  
        System.out.println(sentenceText);  
        System.out.println();  
  
        // 获取第二个句子  
        CoreSentence sentence = document.sentences().get(1);  
  
        // 获取第二个句子的词性标签列表  
        List<String> posTags = sentence.posTags();  
        System.out.println("Example: pos tags");  
        System.out.println(posTags);  
        System.out.println();  
  
        // 获取第二个句子的命名实体识别标签列表  
        List<String> nerTags = sentence.nerTags();  
        System.out.println("Example: ner tags");  
        System.out.println(nerTags);  
        System.out.println();  
  
        // 获取第二个句子的成分句法分析树  
        Tree constituencyParse = sentence.constituencyParse();  
        System.out.println("Example: constituency parse");  
        System.out.println(constituencyParse);  
        System.out.println();  
  
        // 获取第二个句子的依赖分析图  
        SemanticGraph dependencyParse = sentence.dependencyParse();  
        System.out.println("Example: dependency parse");  
        System.out.println(dependencyParse);  
        System.out.println();  
  
        // 获取第五个句子中的KBP关系  
        List<RelationTriple> relations = document.sentences().get(4).relations();  
        System.out.println("Example: relation");  
        System.out.println(relations.get(0));  
        System.out.println();  
  
        // 获取第二个句子中的实体提及  
        List<CoreEntityMention> entityMentions = sentence.entityMentions();  
        System.out.println("Example: entity mentions");  
        System.out.println(entityMentions);  
        System.out.println();  
  
        // 获取实体提及之间的共指关系  
        CoreEntityMention originalEntityMention = document.sentences().get(3).entityMentions().get(1);  
        System.out.println("Example: original entity mention");  
        System.out.println(originalEntityMention);  
        System.out.println("Example: canonical entity mention");  
        System.out.println(originalEntityMention.canonicalEntityMention().get());  
        System.out.println();  
  
        // 获取文档级的共指信息  
        Map<Integer, CorefChain> corefChains = document.corefChains();  
        System.out.println("Example: coref chains for document");  
        System.out.println(corefChains);  
        System.out.println();  
  
        // 获取文档中的引用  
        List<CoreQuote> quotes = document.quotes();  
        CoreQuote quote = quotes.get(0);  
        System.out.println("Example: quote");  
        System.out.println(quote);  
        System.out.println();  
  
        // 引用的原始发言者  
        // 注意，quote.speaker() 返回一个 Optional        System.out.println("Example: original speaker of quote");  
        System.out.println(quote.speaker().get());  
        System.out.println();  
  
        // 引用的规范发言者  
        System.out.println("Example: canonical speaker of quote");  
        System.out.println(quote.canonicalSpeaker().get());  
        System.out.println();  
  
    }  
}
```

对于程序的输出如下:
```powershell
Example: token
he-4

Example: sentence
Joe Smith was born in California.

Example: pos tags
[IN, CD, ,, PRP, VBD, IN, NNP, ,, NNP, IN, DT, NN, .]

Example: ner tags
[O, DATE, O, O, O, O, CITY, O, COUNTRY, O, O, DATE, O]

Example: constituency parse
(ROOT (S (PP (IN In) (NP (CD 2017))) (, ,) (NP (PRP he)) (VP (VBD went) (PP (IN to) (NP (NNP Paris) (, ,) (NNP France))) (PP (IN in) (NP (DT the) (NN summer)))) (. .)))

Example: dependency parse
-> went/VBD (root)
  -> 2017/CD (obl:in)
    -> In/IN (case)
  -> ,/, (punct)
  -> he/PRP (nsubj)
  -> Paris/NNP (obl:to)
    -> to/IN (case)
    -> ,/, (punct)
    -> France/NNP (appos)
  -> summer/NN (obl:in)
    -> in/IN (case)
    -> the/DT (det)
  -> ./. (punct)


Example: relation
1.0	Jane Smith	per:siblings	Joe Smith

Example: entity mentions
[2017, Paris, France, summer, he]

Example: original entity mention
Joe
Example: canonical entity mention
Joe Smith

Example: coref chains for document
{21=CHAIN21-["Joe Smith" in sentence 1, "he" in sentence 2, "His" in sentence 3, "Joe" in sentence 4, "He" in sentence 5, "his" in sentence 5, "Joe 's" in sentence 6], 24=CHAIN24-["his sister Jane Smith" in sentence 5, "Jane" in sentence 6, "she" in sentence 6]}

Example: quote
"That was delicious!"

Example: original speaker of quote
Joe

Example: canonical speaker of quote
Joe Smith
```

# 其他程序语言

References:
- [Using CoreNLP within other programming languages and packages - CoreNLP (stanfordnlp.github.io)](https://stanfordnlp.github.io/CoreNLP/other-languages.html)

如，Python的程序语言的其他包如下:
![image](https://github.com/inmango/CoreNLP-Learning/assets/105225701/9a6d5c6e-e518-43a6-8653-92c3473eadc9)


