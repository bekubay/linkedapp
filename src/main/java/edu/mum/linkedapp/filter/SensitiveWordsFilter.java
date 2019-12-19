package edu.mum.linkedapp.filter;

import edu.mum.linkedapp.bo.AddPostBO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class SensitiveWordsFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordsFilter.class);

    private TrieNode rootNode = new TrieNode();

    @Around("execution(* edu.mum.linkedapp.controller.PostController.submitPost(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        String m = proceedingJoinPoint.getSignature().getName();
        boolean hasUnhealth = false;
        AddPostBO postBO = (AddPostBO)proceedingJoinPoint.getArgs()[0];
        hasUnhealth = this.filter(postBO.getContent());
        postBO.setUnhealth(hasUnhealth);
        proceedingJoinPoint.getArgs()[0] = postBO;
        Object ret = null;
        try {
            ret = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("After " + m + " returned " + ret);
        return ret;
    }

    @PostConstruct
    public void init() {
        try (
                FileInputStream is = new FileInputStream(new ClassPathResource("static/files/sensitive-words.txt").getFile());
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                this.addKeyword(keyword);
            }
        } catch (IOException e) {
            logger.error("Sensitive Word loading fail: " + e.getMessage());
        }
    }

    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            tempNode = subNode;

            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    public boolean filter(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }

        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        StringBuilder sb = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }

            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                sb.append(text.charAt(begin));
                position = ++begin;
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
//                sb.append(REPLACEMENT);
//                begin = ++position;
//                tempNode = rootNode;
                return true;
            } else {
                position++;
            }
        }

        // 将最后一批字符计入结果
        sb.append(text.substring(begin));

        return false;
    }

    private boolean isSymbol(Character c) {
        // 0x2E80~0x9FFF Asian
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    private class TrieNode {

        private boolean isKeywordEnd = false;

        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }
    }

}
