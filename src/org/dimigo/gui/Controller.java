package org.dimigo.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller implements Initializable{
        @FXML private ComboBox<String> cbClass1;
        @FXML private ComboBox<String> cbClass2;
        @FXML private ComboBox<String> cbClass3;
        @FXML private ComboBox<SearchType> cbClass4;
        @FXML private Button bntSrc;
        @FXML private ListView<String> listView;
        @FXML private ImageView photo;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            cbClass1.getItems().addAll("2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012",
                    "2013", "2014", "2015", "2016", "2017", "2018", "2019");
            cbClass2.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
            cbClass3.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                    "13", "14","15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");

            ObservableList<SearchType> comboItems = FXCollections.observableArrayList();
            comboItems.add(new SearchType("아스날","Arsenal"));
            comboItems.add(new SearchType("아스톤 빌라","Aston Villa"));
            comboItems.add(new SearchType("본머스","Bournemouth"));
            comboItems.add(new SearchType("브라이튼","Brighton & Hove Albion"));
            comboItems.add(new SearchType("번리","Burnley"));
            comboItems.add(new SearchType("첼시","Chelsea"));
            comboItems.add(new SearchType("팰리스","Crystal Palace"));
            comboItems.add(new SearchType("에버튼","Everton"));
            comboItems.add(new SearchType("레스터","Leicester"));
            comboItems.add(new SearchType("리버풀","Liverpool"));
            comboItems.add(new SearchType("맨시티","Manchester City"));
            comboItems.add(new SearchType("맨유","Manchester United"));
            comboItems.add(new SearchType("뉴캐슬","Newcastle"));
            comboItems.add(new SearchType("노리치","Norwich"));
            comboItems.add(new SearchType("셰필드","Sheffield"));
            comboItems.add(new SearchType("사우스햄튼","Southampton"));
            comboItems.add(new SearchType("토트넘","Tottenham"));
            comboItems.add(new SearchType("왓포드","Watford"));
            comboItems.add(new SearchType("웨스트햄","West Ham"));
            comboItems.add(new SearchType("울버햄튼","Wolverhampton"));
            cbClass4.setItems(comboItems);

            Image image = new Image(getClass().getResourceAsStream("/photo/rogo.png"));
            photo.setFitHeight(150);
            photo.setFitWidth(200);
            photo.setImage(image);
        }

        public void search(ActionEvent event){
            List<String> list = new ArrayList<>();
            try{
                String year = cbClass1.getValue();
                String month = cbClass2.getValue();
                String day = cbClass3.getValue();
                if(year == null || month == null || day == null){
                    list.add("날짜를 정확히 입력해주세요");
                }else {
                    Document doc = Jsoup.connect("https://www.goal.com/kr/%EA%B2%B0%EA%B3%BC/" + year + "-" + month + "-" + day).get();
                    String[] s1 = doc.html().split("class=\"competition-name\">");
                    list.add(year + "|" + month + "|" + day);
                    for (int i = 1; i < s1.length; i++) {
                        String title = s1[i].split("<")[0];
                        title = title.trim();
                        if (!title.equals("프리미어 리그"))
                            continue;
                        String[] s2 = s1[i].split("class=\"match-row");
                        if (s2.length == 2) {
                            list.add("경기 일정이 없습니다");
                        } else {
                            for (int j = 2; j < s2.length; j++) {
                                s2[j] = s2[j].replace("&amp;", "&");
                                String score1 = s2[j].split("data-bind=\"scoreHome\">")[1].split("<")[0];
                                String score2 = s2[j].split("data-bind=\"scoreAway\">")[1].split("<")[0];
                                String[] ns = s2[j].split("class=\"team-name\">");
                                String name1 = ns[1].split("<")[0];
                                String name2 = ns[2].split("<")[0];
                                list.add(name1 + " " + score1 + " : " + score2 + " " + name2);
                            }
                        }
                        for (String value : list) {
                            System.out.println(value);
                        }
                    }
                }
            } catch (IOException e) {
                list.clear();
                list.add("알 수 없는 오류 발생");
            }
            ObservableList<String> arr = FXCollections.observableArrayList(list);
            listView.setItems(arr);
        }

        public void setPhoto(ActionEvent event){
            SearchType item = cbClass4.getSelectionModel().getSelectedItem();
            String type = item.getValue();
            Image image = new Image(getClass().getResourceAsStream("/photo/"+ type +".png"));
            photo.setFitHeight(150);
            photo.setFitWidth(200);
            photo.setImage(image);
        }
    }
class SearchType {
    private String text;
    private String value;

    public SearchType(String text, String value) {
        this.text = text;
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return text;
    }
}

