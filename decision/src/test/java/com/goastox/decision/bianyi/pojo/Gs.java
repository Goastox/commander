package com.goastox.decision.bianyi.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.alibaba.fastjson.JSON;
import com.goastox.decision.bianyi.util.TextUtil;

/**
 * @author PuHaiyang
 * @createTime 2016年6月10日 下午7:46:33
 * @email 761396462@qq.com
 * @function LL(1)文法
 *
 */
public class Gs implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Gs() {
        super();
        gsArray = new ArrayList<String>();
        nvSet = new TreeSet<Character>();
        ntSet = new TreeSet<Character>();
        firstMap = new HashMap<Character, TreeSet<Character>>();
        followMap = new HashMap<Character, TreeSet<Character>>();
        selectMap = new TreeMap<Character, HashMap<String, TreeSet<Character>>>();
    }

    private String[][] analyzeTable;

    /**
     * Select集合
     */
    private TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap;
    /**
     * LL（1）文法产生集合
     */
    private ArrayList<String> gsArray;
    /**
     * 表达式集合
     */
    private HashMap<Character, ArrayList<String>> expressionMap;
    /**
     * 开始符
     */
    private Character s;
    /**
     * Vn非终结符集合
     */
    private TreeSet<Character> nvSet;
    /**
     * Vt终结符集合
     */
    private TreeSet<Character> ntSet;
    /**
     * First集合
     */
    private HashMap<Character, TreeSet<Character>> firstMap;
    /**
     * Follow集合
     */
    private HashMap<Character, TreeSet<Character>> followMap;

    public String[][] getAnalyzeTable() {
        return analyzeTable;
    }

    public void setAnalyzeTable(String[][] analyzeTable) {
        this.analyzeTable = analyzeTable;
    }

    public TreeMap<Character, HashMap<String, TreeSet<Character>>> getSelectMap() {
        return selectMap;
    }

    public void setSelectMap(TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap) {
        this.selectMap = selectMap;
    }

    public HashMap<Character, TreeSet<Character>> getFirstMap() {
        return firstMap;
    }

    public void setFirstMap(HashMap<Character, TreeSet<Character>> firstMap) {
        this.firstMap = firstMap;
    }

    public HashMap<Character, TreeSet<Character>> getFollowMap() {
        return followMap;
    }

    public void setFollowMap(HashMap<Character, TreeSet<Character>> followMap) {
        this.followMap = followMap;
    }

    public HashMap<Character, ArrayList<String>> getExpressionMap() {
        return expressionMap;
    }

    public void setExpressionMap(HashMap<Character, ArrayList<String>> expressionMap) {
        this.expressionMap = expressionMap;
    }

    public ArrayList<String> getGsArray() {
        return gsArray;
    }

    public void setGsArray(ArrayList<String> gsArray) {
        this.gsArray = gsArray;
    }

    public Character getS() {
        return s;
    }

    public void setS(Character s) {
        this.s = s;
    }

    public TreeSet<Character> getNvSet() {
        return nvSet;
    }

    public void setNvSet(TreeSet<Character> nvSet) {
        this.nvSet = nvSet;
    }

    public TreeSet<Character> getNtSet() {
        return ntSet;
    }

    public void setNtSet(TreeSet<Character> ntSet) {
        this.ntSet = ntSet;
    }

    /**
     * 获取非终结符集与终结符集
     *
     * @param gsArray
     * @param nvSet
     * @param ntSet
     */
    public void getNvNt() {

        gsArray.stream().forEach(x -> {
            String[] split = x.split("->");
            nvSet.add(split[0].charAt(0));
        });

        gsArray.stream().forEach( x -> {
            String[] split = x.split("->");
            for (int i = 0; i < split[1].length(); i++) {
                char c = split[1].charAt(i);
                if(!nvSet.contains(c)){
                    ntSet.add(c);
                }
            }
        });

        System.out.println("非终结符" + JSON.toJSONString(nvSet));
        System.out.println("终结符" + JSON.toJSONString(ntSet));
    }

    /**
     * 初始化表达式集合
     */
    public void initExpressionMaps() {
        expressionMap = new HashMap<Character, ArrayList<String>>();
        for (String gsItem : gsArray) {
            String[] nvNtItem = gsItem.split("->");
            String charItemStr = nvNtItem[0];
            String charItemRightStr = nvNtItem[1];
            char charItem = charItemStr.charAt(0);
            if (!expressionMap.containsKey(charItem)) {
                ArrayList<String> expArr = new ArrayList<String>();
                expArr.add(charItemRightStr);
                expressionMap.put(charItem, expArr);
            } else {
                ArrayList<String> expArr = expressionMap.get(charItem);
                expArr.add(charItemRightStr);
                expressionMap.put(charItem, expArr);
            }
        }
        System.out.println("初始化表达式集合" + JSON.toJSONString(expressionMap));
    }

    /**
     * 获取First集
     */


    public void getFirst(){
        nvSet.stream().map( x -> {
            firstMap.put(x, new TreeSet<>());
            return x;
        }).forEach(x -> {
            ArrayList<String> strings = expressionMap.get(x);
            strings.stream().forEach( s -> {
                    aaa(x, s.charAt(0));
            });
        });
        System.out.println("First集: " + JSON.toJSONString(firstMap));
    }


    public void aaa(char x, char c){
        if(ntSet.contains(c)){
            firstMap.get(x).add(c);
        }else {
            ArrayList<String> strings1 = expressionMap.get(c);
            strings1.stream().forEach( s1 -> {
                aaa(x , s1.charAt(0));
            });
        }
    }


    /**
     * 获取Follow集合
     */
    public void getFollow() {
        for (Character tempKey : nvSet) {
            TreeSet<Character> tempSet = new TreeSet<Character>();
            followMap.put(tempKey, tempSet);
        }

        // 遍历所有Nv,求出它们的First集合
        for (Character charItem : nvSet) {
            Set<Character> keySet = expressionMap.keySet();
            for (Character keyCharItem : keySet) {
                ArrayList<String> charItemArray = expressionMap.get(keyCharItem);//c "+TC","ε"
                for (String itemCharStr : charItemArray) {
                    TreeSet<Character> itemSet = followMap.get(charItem);
                    calcFollow(charItem, charItem, keyCharItem, itemCharStr, itemSet);
                }
            }
        }

        System.out.println("Follow集合: " + JSON.toJSONString(followMap));
    }

    /**
     * 计算Follow集
     *
     * @param putCharItem
     *            正在查询item
     * @param charItem
     *            待找item
     * @param keyCharItem
     *            节点名
     * @param itemCharStr
     *            符号集
     * @param itemSet
     *            结果集合
     */
    private void calcFollow(Character putCharItem, Character charItem, Character keyCharItem, String itemCharStr,
                            TreeSet<Character> itemSet) {
        ///
        // （1）A是S（开始符)，加入#
        if (charItem.equals(s)) {
            itemSet.add('#');
//            System.out.println("---------------find S:" + charItem + "   ={#}+Follow(E)");
            followMap.put(putCharItem, itemSet);
            // return;
        }
        // (2)Ab,=First(b)-ε,直接添加终结符
        if (TextUtil.containsAb(ntSet, itemCharStr, charItem)) {
            Character alastChar = TextUtil.getAlastChar(itemCharStr, charItem);
//            System.out.println("---------------find Ab:" + itemCharStr + "    " + charItem + "   =" + alastChar);
            itemSet.add(alastChar);
            followMap.put(putCharItem, itemSet);
            // return;
        }
        // (2).2AB,=First(B)-ε,=First(B)-ε，添加first集合
        if (TextUtil.containsAB(nvSet, itemCharStr, charItem)) {
            Character alastChar = TextUtil.getAlastChar(itemCharStr, charItem);
//            System.out.println("---------------find AB:" + itemCharStr + "    " + charItem + "   =First(" + alastChar + ")");
            TreeSet<Character> treeSet = firstMap.get(alastChar);
            itemSet.addAll(treeSet);
            if (treeSet.contains('ε')) {
                itemSet.add('#');
            }
            itemSet.remove('ε');
            followMap.put(putCharItem, itemSet);
            ///
            if (TextUtil.containsbAbIsNull(nvSet, itemCharStr, charItem, expressionMap)) {
                char tempChar = TextUtil.getAlastChar(itemCharStr, charItem);
//                System.out.println("tempChar:" + tempChar + "  key" + keyCharItem);
                if (!keyCharItem.equals(charItem)) {
//                    System.out.println("---------------find tempChar bA: " + "tempChar:" + tempChar + keyCharItem
//                            + "   " + itemCharStr + "    " + charItem + "   =Follow(" + keyCharItem + ")");
                    Set<Character> keySet = expressionMap.keySet();
                    for (Character keyCharItems : keySet) {
                        ArrayList<String> charItemArray = expressionMap.get(keyCharItems);
                        for (String itemCharStrs : charItemArray) {
                            calcFollow(putCharItem, keyCharItem, keyCharItems, itemCharStrs, itemSet);
                        }
                    }
                }
            }
        }
        // (3)B->aA,=Follow(B),添加followB
        if (TextUtil.containsbA(nvSet, itemCharStr, charItem, expressionMap)) {
            if (!keyCharItem.equals(charItem)) {
//                System.out.println("---------------find bA: " + keyCharItem + "   " + itemCharStr + "    " + charItem
//                        + "   =Follow(" + keyCharItem + ")");
                Set<Character> keySet = expressionMap.keySet();
                for (Character keyCharItems : keySet) {
                    ArrayList<String> charItemArray = expressionMap.get(keyCharItems);
                    for (String itemCharStrs : charItemArray) {
                        calcFollow(putCharItem, keyCharItem, keyCharItems, itemCharStrs, itemSet);
                    }
                }
            }
        }
    }

    /**
     * 获取Select集合
     */
    public void getSelect() {
        // 遍历每一个表达式
        // HashMap<Character, HashMap<String, TreeSet<Character>>>
        Set<Character> keySet = expressionMap.keySet();
        for (Character selectKey : keySet) {
            ArrayList<String> arrayList = expressionMap.get(selectKey);
            // 每一个表达式
            HashMap<String, TreeSet<Character>> selectItemMap = new HashMap<String, TreeSet<Character>>();
            for (String selectExp : arrayList) {
                /**
                 * 存放select结果的集合
                 */
                TreeSet<Character> selectSet = new TreeSet<Character>();
                // set里存放的数据分3种情况,由selectExp决定
                // 1.A->ε,=follow(A)
                if (TextUtil.isEmptyStart(selectExp)) {
                    selectSet = followMap.get(selectKey);
                    selectSet.remove('ε');
                    selectItemMap.put(selectExp, selectSet);
                }
                // 2.Nt开始,=Nt
                // <br>终结符开始
                if (TextUtil.isNtStart(ntSet, selectExp)) {
                    selectSet.add(selectExp.charAt(0));
                    selectSet.remove('ε');
                    selectItemMap.put(selectExp, selectSet);
                }
                // 3.Nv开始，=first(Nv)
                if (TextUtil.isNvStart(nvSet, selectExp)) {
                    selectSet = firstMap.get(selectKey);
                    selectSet.remove('ε');
                    selectItemMap.put(selectExp, selectSet);
                }
                selectMap.put(selectKey, selectItemMap);
            }
        }

        System.out.println("select集： " + JSON.toJSONString(selectMap));
    }

    /**
     * 生成预测分析表
     */
    public void genAnalyzeTable() throws Exception {
        Object[] ntArray = ntSet.toArray();
        Object[] nvArray = nvSet.toArray();
        // 预测分析表初始化
        analyzeTable = new String[nvArray.length + 1][ntArray.length + 1];

        // 输出一个占位符
        System.out.print("Nv/Nt" + "\t\t");
        analyzeTable[0][0] = "Nv/Nt";
        // 初始化首行
        for (int i = 0; i < ntArray.length; i++) {
            if (ntArray[i].equals('ε')) {
                ntArray[i] = '#';
            }
            System.out.print(ntArray[i] + "\t\t");
            analyzeTable[0][i + 1] = ntArray[i] + "";
        }

        System.out.println("");
        for (int i = 0; i < nvArray.length; i++) {
            // 首列初始化
            System.out.print(nvArray[i] + "\t\t");
            analyzeTable[i + 1][0] = nvArray[i] + "";
            for (int j = 0; j < ntArray.length; j++) {
                String findUseExp = TextUtil.findUseExp(selectMap, Character.valueOf((Character) nvArray[i]),
                        Character.valueOf((Character) ntArray[j]));
                if (null == findUseExp) {
                    System.out.print("\t\t");
                    analyzeTable[i + 1][j + 1] = "";
                } else {
                    System.out.print(nvArray[i] + "->" + findUseExp + "\t\t");
                    analyzeTable[i + 1][j + 1] = nvArray[i] + "->" + findUseExp;
                }
            }
            System.out.println();
        }
    }
}

