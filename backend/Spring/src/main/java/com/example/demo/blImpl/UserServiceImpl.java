package com.example.demo.blImpl;

import com.example.demo.dao.*;
import com.example.demo.po.Entity;
import com.example.demo.po.HistoryEntry;
import com.example.demo.po.Relation;
import com.example.demo.vo.GraphVO;
import com.example.demo.bl.UserService;
import com.example.demo.vo.UserVO;
import com.example.demo.vo.Userform;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.MyStaticValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/29
 * @Description:Implementations of UserService
 */

@Service
public class UserServiceImpl implements UserService{
    //@Autowired
    //UserMapper userMapper;
    @Autowired
    GraphicMapper graphicMapper;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    RelationMapper relationMapper;

    @Autowired
    HistoryMapper historyMapper;

    @Override
    public Integer register(Userform userform) { return 1; }

    @Override
    public UserVO getUser(Integer uid) {return new UserVO();}

    @Override
    public Integer login(Userform userform) {return 1; }

    //语义搜索
    @Override
    public GraphVO search(ArrayList<String> keyWords,int uid){
        GraphVO graphVO=new GraphVO();
        ArrayList<Entity> resultEntities=new ArrayList<>();
        ArrayList<Relation> resultRelations=new ArrayList<>();
        ArrayList<Integer> resultEids=new ArrayList<>();
        ArrayList<Integer> resultRids=new ArrayList<>();
        for(int i=0;i<keyWords.size();i++) {
            String keyWord = keyWords.get(i);
            HistoryEntry historyEntry = new HistoryEntry();
            HistoryEntry inSqlEntry = historyMapper.getHistoryByName(keyWord, uid);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (inSqlEntry == null) {
                historyEntry.setHistory(keyWord);
                historyEntry.setUid(uid);
                historyMapper.addHistory(historyEntry);
                historyMapper.updateTime(historyEntry.getHid(), df.format(new Date()));
            } else {
                historyMapper.updateTime(inSqlEntry.getHid(), df.format(new Date()));
            }

            ArrayList<Integer> eids = entityMapper.getEidsByKey(keyWord);
            ArrayList<Integer> entities = new ArrayList<>();
            ArrayList<Integer> relations = new ArrayList<>();
            entities.addAll(eids);
            for (int j = 0; j < eids.size(); j++) {
                entities.addAll(relationMapper.getSecondEidsByFirstEid(eids.get(j)));
                entities.addAll(relationMapper.getFirstEidsBySecondEid(eids.get(j)));
                relations.addAll(relationMapper.getRidsByEid(eids.get(j)));
            }
            resultEids.addAll(entities);
            resultRids.addAll(relations);
        }
        LinkedHashSet<Integer> entitySet = new LinkedHashSet<>(resultEids);
        LinkedHashSet<Integer> relationSet = new LinkedHashSet<>(resultRids);
        ArrayList<Integer> entitiesWithoutDuplicates = new ArrayList<>(entitySet);
        ArrayList<Integer> relationsWithoutDuplicates = new ArrayList<>(relationSet);
        for (int i = 0; i < entitiesWithoutDuplicates.size(); i++) {
            resultEntities.add(entityMapper.getEntityByEid(entitiesWithoutDuplicates.get(i)));
        }
        for (int i = 0; i < relationsWithoutDuplicates.size(); i++) {
            resultRelations.add(relationMapper.getRelationByRid(relationsWithoutDuplicates.get(i)));
        }
        graphVO.setName("results of \"" + keyWords.toString() +"\" :");
        graphVO.setUid(uid);
        graphVO.setRelations(resultRelations);
        graphVO.setEntities(resultEntities);
        return graphVO;
    }

    //智能问答
    @Override
    public String answer(String question){
        MyStaticValue.isRealName=true;
        String vPattern="v.*";
        String nPattern="n.*";
        int isPeiYin=0;
        int isZuoPin=0;
        int isJueSe=0;
        int isShengYou=0;
        int isHeZuo=0;
        int isDaoYan=0;
        int isZhiZuo=0;
        int isZhiZuoFang=0;
        int isJianDu=0;
        int isGongSi=0;
        int nrNum=0;
        int nzNum=0;
        MyStaticValue.ENV.put(DicLibrary.DEFAULT,"target/classes/default.dic");
        List<Term> terms= DicAnalysis.parse(question).getTerms();
        List<String> persons=new ArrayList<>();
        List<String> animations=new ArrayList<>();
        System.out.println(terms);
        for(int i=0;i<terms.size();i++){
            Term temp=terms.get(i);
            if(temp.getNatureStr().equals("nr")) {
                persons.add(temp.getRealName());
                nrNum++;
                continue;
            }
            if(temp.getNatureStr().equals("nz")) {
                animations.add(temp.getRealName());
                nzNum++;
                continue;
            }
            if(Pattern.matches(nPattern,temp.getNatureStr())) {
                switch(temp.getRealName()){
                    case "作品":
                        isZuoPin=1;
                        break;
                    case "角色":
                        isJueSe=1;
                        break;
                    case "声优":
                        isShengYou=1;
                        break;
                    case "导演":
                        isDaoYan=1;
                        break;
                    case "公司":
                        isGongSi=1;
                        break;
                    case "制作方":
                        isZhiZuoFang=1;
                        break;
                }

                continue;
            }
            if(Pattern.matches(vPattern,temp.getNatureStr())){
                switch(temp.getRealName()){
                    case "配音":
                        isPeiYin=1;
                        break;
                    case "合作":
                        isHeZuo=1;
                        break;
                    case "监督":
                        isJianDu=1;
                        break;
                    case "制作":
                        isZhiZuo=1;
                        break;
                }
                continue;
            }
        }

        String result="";
        //1类问题：XX作品中的所有角色。
        if((nrNum==0&&nzNum==1)&&(isJueSe==1)){
            ArrayList<Integer> eids= entityMapper.getEidsByKey(animations.get(0));
            if(eids.isEmpty()) return "作品查询无结果！";
            int eid=eids.get(0);
            ArrayList<Integer> resultEids=relationMapper.getFirstEidsBySecondEid(eid);
            if(resultEids.isEmpty()) return  "角色查询无结果！";
            result=result+"《"+animations.get(0)+"》中的角色有：\n";
            for(int i=0;i<resultEids.size();i++){
                if(!entityMapper.getEntityByEid(resultEids.get(i)).getCategory().equals("person")) continue;
                String temp=entityMapper.getEntityByEid(resultEids.get(i)).getName();
                result=result+temp;
                if(i==resultEids.size()-1){
                    result=result+"。";
                }
                else{
                    result=result+"、";
                }
            }
            return result;
        }
        //2类问题，XX作品中的所有声优。
        else if((nrNum==0&&nzNum==1)&&(isPeiYin==1||isShengYou==1)){
            ArrayList<Integer> eids= entityMapper.getEidsByKey(animations.get(0));
            if(eids.isEmpty()) return "作品查询无结果！";
            int eid=eids.get(0);
            ArrayList<Integer> actorEids=relationMapper.getFirstEidsBySecondEid(eid);
            if(actorEids.isEmpty()) return  "角色查询无结果！";
            ArrayList<Integer> resultEids=new ArrayList<>();
            for(int i=0;i<actorEids.size();i++){
                resultEids.addAll(relationMapper.getFirstEidsBySecondEid(actorEids.get(i)));
            }
            if(resultEids.isEmpty()) return "声优查询无结果！";
            result=result+"《"+animations.get(0)+"》中的声优有：\n";
            for(int i=0;i<resultEids.size();i++){
                if(!entityMapper.getEntityByEid(resultEids.get(i)).getCategory().equals("actor")) continue;
                String temp=entityMapper.getEntityByEid(resultEids.get(i)).getName();
                result=result+temp;
                if(i==resultEids.size()-1){
                    result=result+"。";
                }
                else{
                    result=result+"、";
                }
            }
            return result;
        }
        //3类问题，XX角色的声优。
        else if((nrNum==1&&nzNum==0)&&(isJueSe==0)&&(isShengYou==1||(isPeiYin==1&&isZuoPin==0))) {
            ArrayList<Integer> eids = entityMapper.getEidsByKey(persons.get(0));
            if (eids.isEmpty()) return "角色查询无结果！";
            int eid = eids.get(0);
            ArrayList<Integer> resultEids = relationMapper.getFirstEidsBySecondEid(eid);
            if (resultEids.isEmpty()) return "声优查询无结果！";
            result = result + "\"" + persons.get(0) + "\"" + "的声优是：\n" + entityMapper.getEntityByEid(resultEids.get(0)).getName() + "。";
            return result;
        }

        else if((nrNum==1&&nzNum==0)&&((isZuoPin==1)||(isDaoYan==1)||(isJianDu==1))){
            ArrayList<Integer> eids=entityMapper.getEidsByKey(persons.get(0));
            if(eids.isEmpty()) return "角色或声优查询无结果！";
            int eid=eids.get(0);
            Entity person=entityMapper.getEntityByEid(eid);
            //4类问题，XX角色所在的作品。
            if(person.getCategory().equals("person")){
                ArrayList<Integer> resultEids=relationMapper.getSecondEidsByFirstEid(eid);
                if(resultEids.isEmpty()) return "作品查询无结果！";
                result=result+"\""+person.getName()+"\""+"所在的作品有：\n";
                for(int i=0;i<resultEids.size();i++){
                    if(!entityMapper.getEntityByEid(resultEids.get(i)).getCategory().equals("animation")) continue;
                    String temp=entityMapper.getEntityByEid(resultEids.get(i)).getName();
                    result=result+temp;
                    if(i==resultEids.size()-1){
                        result=result+"。";
                    }
                    else{
                        result=result+"、";
                    }
                }
                return  result;
            }
            //6类问题，XX声优的所有作品。
            else if(person.getCategory().equals("actor")){
                ArrayList<Integer> personEids=relationMapper.getSecondEidsByFirstEid(eid);
                if(personEids.isEmpty()) return "角色查询无结果！";
                ArrayList<Integer> resultEids=new ArrayList<>();
                for(int i=0;i<personEids.size();i++){
                    resultEids.addAll(relationMapper.getSecondEidsByFirstEid(personEids.get(i)));
                }
                if(resultEids.isEmpty()) return "作品查询无结果！";
                result=result+"\""+person.getName()+"\""+"参与配音的作品有：\n";
                for(int i=0;i<resultEids.size();i++){
                    if(!entityMapper.getEntityByEid(resultEids.get(i)).getCategory().equals("animation")) continue;
                    String temp=entityMapper.getEntityByEid(resultEids.get(i)).getName();
                    result=result+temp;
                    if(i==resultEids.size()-1){
                        result=result+"。";
                    }
                    else{
                        result=result+"、";
                    }
                }
                return result;
            }
            //9类问题，XX导演的所有作品。
            else if(person.getCategory().equals("director")||isDaoYan==1||isJianDu==1){
                ArrayList<Integer> resultEids=relationMapper.getSecondEidsByFirstEid(eid);
                if(resultEids.isEmpty()) return "作品查询无结果！";
                result=result+"\'"+person.getName()+"\""+"导演的作品有：\n";
                for(int i=0;i<resultEids.size();i++){
                    if(!entityMapper.getEntityByEid(resultEids.get(i)).getCategory().equals("animation")) continue;
                    String temp=entityMapper.getEntityByEid(resultEids.get(i)).getName();
                    result=result+temp;
                    if(i==resultEids.size()-1){
                        result=result+"。";
                    }
                    else{
                        result=result+"、";
                    }
                }
                return result;
            }
        }
        //5类问题，XX声优的所有角色。
        else if((nrNum==1&&nzNum==0)&&(isJueSe==1||isPeiYin==1)){
            ArrayList<Integer> eids= entityMapper.getEidsByKey(persons.get(0));
            if(eids.isEmpty()) return "声优查询无结果！";
            int eid=eids.get(0);
            ArrayList<Integer> resultEids=relationMapper.getSecondEidsByFirstEid(eid);
            if(resultEids.isEmpty()) return  "角色查询无结果！";
            result=result+"\""+persons.get(0)+"\"配音的角色有：\n";
            for(int i=0;i<resultEids.size();i++){
                if(!entityMapper.getEntityByEid(resultEids.get(i)).getCategory().equals("person")) continue;
                String temp=entityMapper.getEntityByEid(resultEids.get(i)).getName();
                result=result+temp;
                if(i==resultEids.size()-1){
                    result=result+"。";
                }
                else{
                    result=result+"、";
                }
            }
            return result;
        }
        //7类问题，两个声优合作的作品。
        else if((nrNum==2)&&(isHeZuo==1||isZuoPin==1)){
            ArrayList<Integer> firstCv= entityMapper.getEidsByKey(persons.get(0));
            ArrayList<Integer> secondCv= entityMapper.getEidsByKey(persons.get(1));
            if(firstCv.isEmpty()||secondCv.isEmpty()) return "声优查询无结果！";
            ArrayList<Integer> firstPersons=relationMapper.getSecondEidsByFirstEid(firstCv.get(0));
            ArrayList<Integer> secondPersons=relationMapper.getSecondEidsByFirstEid(secondCv.get(0));
            if(firstPersons.isEmpty()||secondPersons.isEmpty()) return "角色查询无结果！";
            ArrayList<Integer> firstAnimations=new ArrayList<>();
            ArrayList<Integer> secondAnimations=new ArrayList<>();
            for(int i=0;i<firstPersons.size();i++){
                firstAnimations.addAll(relationMapper.getSecondEidsByFirstEid(firstPersons.get(i)));
            }
            for(int i=0;i<secondPersons.size();i++){
                secondAnimations.addAll(relationMapper.getSecondEidsByFirstEid(secondPersons.get(i)));
            }
            if(firstAnimations.isEmpty()||secondAnimations.isEmpty()) return "作品查询无结果！";
            ArrayList<Integer> resultEids=new ArrayList<>(firstAnimations);
            resultEids.retainAll(secondAnimations);
            if(resultEids.isEmpty()) return "合作作品查询无结果！";
            result=result+"\""+persons.get(0)+"\""+"和"+"\""+persons.get(1)+"\""+"合作的作品有：\n";
            for(int i=0;i<resultEids.size();i++){
                if(!entityMapper.getEntityByEid(resultEids.get(i)).getCategory().equals("animation")) continue;
                String temp=entityMapper.getEntityByEid(resultEids.get(i)).getName();
                result=result+temp;
                if(i==resultEids.size()-1){
                    result=result+"。";
                }
                else{
                    result=result+"、";
                }
            }
            return  result;
        }
        //8类问题，XX声优在XX作品中的角色。
        else if((nrNum==1&&nzNum==1)&&(isJueSe==1||isPeiYin==1)){
            ArrayList<Integer> personEids=entityMapper.getEidsByKey(persons.get(0));
            if (personEids.isEmpty()) return "声优查询无结果！";
            ArrayList<Integer> animationEids=entityMapper.getEidsByKey(animations.get(0));
            if (animationEids.isEmpty()) return "作品查询无结果！";
            ArrayList<Integer> cvPersons=relationMapper.getSecondEidsByFirstEid(personEids.get(0));
            ArrayList<Integer> animePersons=relationMapper.getFirstEidsBySecondEid(animationEids.get(0));
            if(cvPersons.isEmpty()||animePersons.isEmpty()) return "角色查询无结果";
            ArrayList<Integer> resultEids=new ArrayList<>(cvPersons);
            resultEids.retainAll(animePersons);
            if(resultEids.isEmpty()) return "该声优在作品中角色查询无结果！";
            result=result+"\""+persons.get(0)+"\""+"在"+"《"+animations.get(0)+"》"+"中的角色有：\n";
            for(int i=0;i<resultEids.size();i++){
                if(!entityMapper.getEntityByEid(resultEids.get(i)).getCategory().equals("person")) continue;
                String temp=entityMapper.getEntityByEid(resultEids.get(i)).getName();
                result=result+temp;
                if(i==resultEids.size()-1){
                    result=result+"。";
                }
                else{
                    result=result+"、";
                }
            }
            return result;
        }
        //10类问题，XX作品的导演。
        else if(nzNum==1&&nrNum==0&&(isDaoYan==1||isJianDu==1)){
            ArrayList<Integer> eids= entityMapper.getEidsByKey(animations.get(0));
            if(eids.isEmpty()) return "作品查询无结果！";
            int eid=eids.get(0);
            ArrayList<Integer> resultEids=relationMapper.getFirstEidsBySecondEid(eid);
            if(resultEids.isEmpty()) return  "导演查询无结果！";
            result=result+"《"+animations.get(0)+"》的导演有：\n";
            for(int i=0;i<resultEids.size();i++){
                if(!entityMapper.getEntityByEid(resultEids.get(i)).getCategory().equals("director")) continue;
                String temp=entityMapper.getEntityByEid(resultEids.get(i)).getName();
                result=result+temp;
                if(i==resultEids.size()-1){
                    result=result+"。";
                }
                else{
                    result=result+"、";
                }
            }
            return result;
        }
        //11类问题，XX作品的制作公司。
        else if(nzNum==1&&nrNum==0&&(isZhiZuo==1||isZhiZuoFang==1)&&isZuoPin==0){
            ArrayList<Integer> eids= entityMapper.getEidsByKey(animations.get(0));
            if(eids.isEmpty()) return "作品查询无结果！";
            int eid=eids.get(0);
            ArrayList<Integer> resultEids=relationMapper.getFirstEidsBySecondEid(eid);
            if(resultEids.isEmpty()) return  "制作方查询无结果！";
            result=result+"《"+animations.get(0)+"》的制作方是：\n";
            for(int i=0;i<resultEids.size();i++){
                if(!entityMapper.getEntityByEid(resultEids.get(i)).getCategory().equals("company")) continue;
                String temp=entityMapper.getEntityByEid(resultEids.get(i)).getName();
                result=result+temp;
                if(i==resultEids.size()-1){
                    result=result+"。";
                }
                else{
                    result=result+"、";
                }
            }
            return result;
        }
        //12类问题，XX公司制作的所有作品。
        else if(nzNum==1&&nrNum==0&&isZuoPin==1){
            ArrayList<Integer> eids= entityMapper.getEidsByKey(animations.get(0));
            if(eids.isEmpty()) return "制作方查询无结果！";
            int eid=eids.get(0);
            if(!entityMapper.getEntityByEid(eid).getCategory().equals("company")) return "制作方查询无结果！";
            ArrayList<Integer> resultEids=relationMapper.getSecondEidsByFirstEid(eid);
            if(resultEids.isEmpty()) return  "作品查询无结果！";
            result=result+"\""+animations.get(0)+"\""+"的作品有：\n";
            for(int i=0;i<resultEids.size();i++){
                if(!entityMapper.getEntityByEid(resultEids.get(i)).getCategory().equals("animation")) continue;
                String temp=entityMapper.getEntityByEid(resultEids.get(i)).getName();
                result=result+temp;
                if(i==resultEids.size()-1){
                    result=result+"。";
                }
                else{
                    result=result+"、";
                }
            }
            return result;
        }

        System.out.println(result);
        return "未定义问题！";
    }

    //获取完整关键词
    @Override
    public ArrayList<String> supplement(String keyWord){
        ArrayList<String> results=entityMapper.getEntityNamesByKey(keyWord);
        LinkedHashSet<String> hashSet=new LinkedHashSet<>(results);
        ArrayList<String> resultsWithoutDuplicates=new ArrayList<>(hashSet);
        return resultsWithoutDuplicates;
    }
    //个性化推荐
    @Override
    public ArrayList<String> recommend(int uid){
        //System.out.println(uid);
        ArrayList<String> results=new ArrayList<>();
        ArrayList<Integer> allEids= entityMapper.getEidsByGid(1);
        ArrayList<String> histories=historyMapper.getHistoriesByUid(uid);
        int hNum=histories.size();
        if(hNum>3) hNum=3;
        int rNum=6-hNum;
        ArrayList<Integer> randoms=new ArrayList<>();
        Random r=new Random();
        while(randoms.size()!=rNum){
            int num=r.nextInt(allEids.size());
            if(!randoms.contains(num)){
                randoms.add(num);
            }
        }
        while(randoms.size()!=6){
            int num=r.nextInt(histories.size());
            if(!randoms.contains(num)){
                randoms.add(num);
            }
        }
        for(int i=0;i<rNum;i++){
            results.add(entityMapper.getEntityByEid(allEids.get(randoms.get(i))).getName());
        }
        for(int i=rNum;i<6;i++){
            results.add(histories.get(randoms.get(i)));
        }
        return results;
    }

    //获取历史记录
    @Override
    public ArrayList<String> getHistory(int uid){
        ArrayList<String> results=historyMapper.getHistoriesByUid(uid);
        System.out.println(results);
        return new ArrayList<>(results.subList(0, Math.min(results.size(),6)));
    }

    //删除单条历史记录
    @Override
    public Integer deleteHistory(int uid, String key){
        try{
            historyMapper.deleteByName(key,uid);
        } catch (Exception e){
            return 1;
        }
        return 0;
    }

    //清除历史记录
    @Override
    public Integer clearHistories(int uid){
        try{
            historyMapper.deleteByUid(uid);
        }catch (Exception e){
            return 1;
        }
        return 0;
    }

    //获取指定id的节点信息
    @Override
    public GraphVO searchByEid(int eid, int uid){
        GraphVO graphVO=new GraphVO();
        ArrayList<Entity> resultEntities=new ArrayList<>();
        ArrayList<Relation> resultRelations=new ArrayList<>();
        Entity entity= entityMapper.getEntityByEid(eid);
        HistoryEntry historyEntry=new HistoryEntry();
        HistoryEntry inSqlEntry=historyMapper.getHistoryByName(entity.getName(),uid);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (inSqlEntry == null) {
            historyEntry.setHistory(entity.getName());
            historyEntry.setUid(uid);
            historyMapper.addHistory(historyEntry);
            historyMapper.updateTime(historyEntry.getHid(), df.format(new Date()));
        } else {
            historyMapper.updateTime(inSqlEntry.getHid(), df.format(new Date()));
        }
        ArrayList<Integer> entities = new ArrayList<>();
        ArrayList<Integer> relations = new ArrayList<>();
        entities.add(eid);
        entities.addAll(relationMapper.getSecondEidsByFirstEid(eid));
        entities.addAll(relationMapper.getFirstEidsBySecondEid(eid));
        relations.addAll(relationMapper.getRidsByEid(eid));
        LinkedHashSet<Integer> entitySet=new LinkedHashSet<>(entities);
        LinkedHashSet<Integer> relationSet=new LinkedHashSet<>(relations);
        ArrayList<Integer> entitiesWithoutDuplicates = new ArrayList<>(entitySet);
        ArrayList<Integer> relationsWithoutDuplicates = new ArrayList<>(relationSet);
        for (int i = 0; i < entitiesWithoutDuplicates.size(); i++) {
            resultEntities.add(entityMapper.getEntityByEid(entitiesWithoutDuplicates.get(i)));
        }
        for (int i = 0; i < relationsWithoutDuplicates.size(); i++) {
            resultRelations.add(relationMapper.getRelationByRid(relationsWithoutDuplicates.get(i)));
        }
        graphVO.setName("results of Eid: \"" + eid +"\" :");
        graphVO.setUid(uid);
        graphVO.setRelations(resultRelations);
        graphVO.setEntities(resultEntities);
        return graphVO;
    }
}
