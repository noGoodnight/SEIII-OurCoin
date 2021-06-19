package com.example.demo.blImpl;

import com.example.demo.dao.EntityMapper;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.library.SynonymsLibrary;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.MyStaticValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class dic {
    public static void main(String[] args) {
        String vPattern="v.*";
        String nPattern="n.*";
        MyStaticValue.isRealName=true;
        MyStaticValue.ENV.put(DicLibrary.DEFAULT,"backend/Spring/src/main/resources/default.dic");
        //DicLibrary.insert(DicLibrary.DEFAULT, "水树奈奈", "nr", 1000);
        KeyWordComputer kwc = new KeyWordComputer(5);
        String content = "【MAPPA】的作品有";
        Result result= DicAnalysis.parse(content);
        List<Term> terms= result.getTerms();
        List<String> persons=new ArrayList<>();
        List<String> verbs=new ArrayList<>();
        List<String> animations=new ArrayList<>();
        List<String> types=new ArrayList<>();
        System.out.println(verbs.isEmpty());
        for(int i=0;i<terms.size();i++){
            Term temp=terms.get(i);
            if(temp.getNatureStr().equals("nr")) {
                persons.add(temp.getRealName());
                continue;
            }
            if(temp.getNatureStr().equals("nz")) {
                animations.add(temp.getRealName());
                continue;
            }
            if(Pattern.matches(nPattern,temp.getNatureStr())) {
                types.add(temp.getRealName());
                continue;
            }
            if(Pattern.matches(vPattern,temp.getNatureStr())){
                verbs.add(temp.getRealName());
                continue;
            }
        }
        ArrayList<String> aaa=new ArrayList<>();
        aaa.add("参与");
        aaa.add("啊啊啊");
        aaa.add("有");
        aaa.retainAll(verbs);
        System.out.println(aaa);
        System.out.println(persons);
        System.out.println(animations);
        System.out.println(types);
        System.out.println(verbs);
        String resultStr="";
        resultStr=persons.toString()+animations.toString()+types.toString()+verbs.toString();
        System.out.println(resultStr);
    }
//        Random r=new Random();
//        for(int i=0;i<100;i++){
//            System.out.println(r.nextInt(15));
//        }
    //}
}
