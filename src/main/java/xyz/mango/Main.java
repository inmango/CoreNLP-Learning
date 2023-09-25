package xyz.mango;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.trees.*;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.StringUtils;

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
        // 注意，quote.speaker() 返回一个 Optional
        System.out.println("Example: original speaker of quote");
        System.out.println(quote.speaker().get());
        System.out.println();

        // 引用的规范发言者
        System.out.println("Example: canonical speaker of quote");
        System.out.println(quote.canonicalSpeaker().get());
        System.out.println();

        // 将句法分析树保存为图像文件
        saveTreeAsImage(constituencyParse, "constituency_parse.png");
    }

    // 保存树结构为图像文件
    public static void saveTreeAsImage(Tree tree, String filename) {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        // 设置字体和颜色
        Font font = new Font("Arial", Font.PLAIN, 14);
        graphics.setFont(font);
        graphics.setColor(Color.black);

        // 将树结构转换为文本形式
        String treeText = tree.toString();

        // 拆分文本为行
        List<String> treeLines = List.of(treeText.split("\n"));

        // 逐行绘制树结构
        int y = 20;
        for (String line : treeLines) {
            graphics.drawString(line, 20, y);
            y += 20; // 调整行间距
        }

        // 保存图像到文件
        try {
            ImageIO.write(image, "PNG", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
