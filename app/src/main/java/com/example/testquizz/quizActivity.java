package com.example.testquizz;

import static com.example.testquizz.startActivity.QUESTION_TYPE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;
import java.util.List;


public class quizActivity extends AppCompatActivity {

    private AppCompatTextView questionView;
    private RadioGroup answerGroup;
    private RadioButton answerA, answerB, answerC;
    private CountDownTimer countDownTimer;

    private Button nextButton, levelView;
    private TextView  stateView;

    private VariantManager manager;

    private String userAnswer = "";

    private  Bundle bundle ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        bundle = getIntent().getExtras();

        manager = new VariantManager(getData());

        loadViews();

        loadDataToView();

        setListeners();

    }

    private void setListeners() {

        answerA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                userAnswer = answerA.getText().toString();
            }
        });

        answerB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                userAnswer = answerB.getText().toString();
            }
        });

        answerC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                userAnswer = answerC.getText().toString();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (userAnswer.isEmpty()) {
                    Toast.makeText(quizActivity.this, "Variant tanla!!!", Toast.LENGTH_SHORT).show();
                } else {

                    boolean hasNext = manager.checkAnswer(userAnswer);

                    userAnswer = "";

                    if (hasNext) {
                        loadDataToView();
                    } else {

                        stateView.setText("T:" + manager.getTrueCount() + " W:" + manager.getWrongCount());
                        levelView.setText(manager.getLevel() + 1 + "");

                        Toast.makeText(quizActivity.this, "Tugadi", Toast.LENGTH_SHORT).show();

                        ResultData resultData = new ResultData(
                                manager.getTrueCount(),
                                manager.getWrongCount(),
                                manager.getTotalQuestions()
                        );


                        Intent intent = new Intent(quizActivity.this,resultActivity.class);

                        intent.putExtra(resultActivity.KEY_RESULT,resultData);
                        resultLauncher.launch(intent);
                    }

                }
            }
        });

    }

    private void loadViews() {
        stateView = findViewById(R.id.state_view);
        levelView = findViewById(R.id.level_view);
        questionView = findViewById(R.id.question_view);
        answerGroup = findViewById(R.id.answer_group);
        answerA = findViewById(R.id.answer_1);
        answerB = findViewById(R.id.answer_2);
        answerC = findViewById(R.id.answer_3);
        nextButton = findViewById(R.id.next_button);
    }

    private void loadDataToView() {

        answerGroup.clearCheck();
        loadTimer();

        levelView.setText(manager.getLevel() + 1 + "");

        questionView.setText(manager.getQuestion());
        answerA.setText(manager.getVariantA());
        answerB.setText(manager.getVariantB());
        answerC.setText(manager.getVariantC());

    }

    private void loadTimer() {
        countDownTimer = new CountDownTimer(20_000,1_000) {
            @Override
            public void onTick(long l) {
                long deltaTime = Math.round(l*1.0/1000);
                if(deltaTime<=20&&deltaTime>15){
                    stateView.setTextColor(Color.parseColor("#04990A"));
                }else if(deltaTime<=15&&deltaTime>10){
                    stateView.setTextColor(Color.parseColor("#ffeb3b"));
                }else if(deltaTime<=10&&deltaTime>5){
                    stateView.setTextColor(Color.parseColor("#FF5722"));
                }else if(deltaTime<=5){
                    stateView.setTextColor(Color.parseColor("#F44336"));
                }
                stateView.setText(String.valueOf(deltaTime));
            }

            @Override
            public void onFinish() {
                stateView.setText(String.valueOf(0));
                userAnswer = "???";
                nextButton.performClick();
            }

        };
        countDownTimer.start();
    }

    ActivityResultLauncher<Intent>  resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
           int code = result.getResultCode();
           if(code == 0){
               quizActivity.this.finish();

           }else if(code == 1){
           bundle.putInt(QUESTION_TYPE,1);
               manager = new VariantManager(getData());
           loadDataToView();
           }else if(code == 2){
               bundle.putInt(QUESTION_TYPE,2);
               manager = new VariantManager(getData());
               loadDataToView();
           }else if(code == 3){
               bundle.putInt(QUESTION_TYPE,3);
               manager = new VariantManager(getData());
               loadDataToView();
           }else if(code == 4){
               bundle.putInt(QUESTION_TYPE,4);
               manager = new VariantManager(getData());
               loadDataToView();
           }
        }
    });


    private List<VariantData> getData() {
        List<VariantData> list = new ArrayList<>();

        if(bundle.getInt(QUESTION_TYPE) == 1){

            VariantData data1 = new VariantData(
                    "Ma’lumotlar omborini boshqarish tizimlariga misol keltirilganlardan qaysi qator to’g’ri?",
                    "MySQL, MS Access, OpenOffice.org Base, Cache, IMS, Firebird",
                    new String[]{"Power Point, Excel, MySQL va DreamWeaver", "MySQL, MS Access, OpenOffice.org Base, Cache, IMS, Firebird", "Access, Front Page, Publisher va Paint"}
            );
            VariantData data2 = new VariantData(
                    "Access dasturining asosiy menyulari to’liq keltirilgan qatorni toping.",
                    "файл, поля, главная, создание, внешние данные, работа с базами данных, таблица",
                    new String[]{"файл, поля, главная, создание, внешние данные, работа с базами данных, таблица", "файл, поля, главная, работа с базами данных, таблица", "файл, поля, главная, создание, внешние данные, работа с базами данных, таблица, окно, справка"}
            );
            VariantData data3 = new VariantData(
                    "MS Access 2010dagi tasvirlanadigan maydon turlari necha xil bo’ladi?",
                    "10", new String[]{"8", "12", "10"}
            );
            VariantData data4 = new VariantData(
                    "Izlab topilgan ma’lumotlarni bekor qilish uchun Glavnaya menyusining qaysi bo’limi tanlanadi?",
                    "Фильтр", new String[]{"Связать", "Фильтр", "Таблицы"}
            );
            VariantData data5 = new VariantData(
                    "Obyektga mo’lgallangan dasturlash tillari to’g’ri keltirilgan javobni belgilang.",
                    "Delphi", new String[]{"Pascal", "Delphi", "C++"}
            );
            VariantData data6 = new VariantData(
                    "Ilovalar yana qanday nomlar bilan yuritiladi?",
                    "amaliy dasturlar", new String[]{"amaliy dasturlar", "dasturiy ta’minot", "texnik ta’minot"}
            );
            VariantData data7 = new VariantData(
                    "Dastur matnini kompyuter tusunadigan mashina kodiga o’girib beruvchi qurilma nima deb ataladi?",
                    "translyator", new String[]{"visual dasturlash", "assembler", "translyator"}
            );
            VariantData data8 = new VariantData(
                    "Mikroprossesorning buyruqlari yana qanday yuritiladi?",
                    "mashina kodi", new String[]{"mashina kodi", "umumlashgan dasturlar", "assembler"}
            );
            VariantData data9 = new VariantData(
                    "Tuzilmaviy dasturlash tili to’g’ri keltirilgan qatorni toping.",
                    "Turbo paskal", new String[]{"Delphi", "Turbo paskal", "Java"}
            );
            VariantData data10 = new VariantData(
                    "Delphi nomi qaerdan olingan?",
                    "Yunonistondagi ibodatxona nomi", new String[]{"Misrdagi ibodatxona nomi", "Faylasufning ismi", "Yunonistondagi ibodatxona nomi"}
            );
            VariantData data11 = new VariantData(
                    "Agar dasturda matn belgisi belgilangan miqdordan oshsa bu haqdagi ma’lumotni chiqaruvchi oynani hosil qilish uchun qaysi tugma lozim bo’ladi?",
                    "ShowMessage", new String[]{"Caption", "ShowMessage", "Button"}
            );
            VariantData data12 = new VariantData(
                    "Ilova oynasining kengligi qaysi xoss orqali o’zgartiriladi?",
                    "Width", new String[]{"Width", "Form", "Caption"}
            );VariantData data13 = new VariantData(
                    "Butun son turidagi kattalikni matn satri turidagi kattalikka o’tkazilish uchun qaysi funksiyasidan foydalanamiz?",
                    "IntToStr", new String[]{"width", "IntToStr", "height"}
            );
            VariantData data14 = new VariantData(
                    "Labelning asosiy xossasi qaysi?",
                    "Caption", new String[]{"Caption", "Button", "Form"}
            );
            VariantData data15 = new VariantData(
                    "Delphi dasturida mantiqiy sanalgan xossa to’g’ri keltirilgan qatorni belgilang.",
                    "Visible", new String[]{"Enabled", "Visible", "Normal"}
            );
            VariantData data16 = new VariantData(
                    "Pascal dasturida matnning tekislanishi qaysi operator yordamida bajariladi?",
                    "Case", new String[]{"Case", "Read", "Write"}
            );
            VariantData data17 = new VariantData(
                    "Delphi dasturida matnning tekislanishi qaysi operator yordamida bajariladi?",
                    "RadioGroup", new String[]{"Check", "RadioGroup", "Edit"}
            );
            VariantData data18 = new VariantData(
                    "Items qanday tarjima qilinadi?",
                    "variant", new String[]{"variant", "inspector", "loyiha"}
            );
            VariantData data19 = new VariantData(
                    "CTRL+X klavishlar kombinatsiyasining vazifasi nima?",
                    "belgilangan qismni o’chirish", new String[]{"buferdan olib qo’yish", "nusxalash", "belgilangan qismni o’chirish"}
            );
            VariantData data20 = new VariantData(
                    "Ma’lumotning Word dasturida abzasni formatlashning necha usli bor?",
                    "4", new String[]{"2", "4", "3"}
            );




          /*  list.add(data1);
            list.add(data2);
            list.add(data3);
            list.add(data4);
            list.add(data5);
            list.add(data6);
            list.add(data7);
            list.add(data8);
            list.add(data9);
            list.add(data10);*/
            list.add(data11);
            list.add(data12);
            list.add(data13);
            list.add(data14);
            list.add(data15);
            list.add(data16);
            list.add(data17);
            list.add(data18);
            list.add(data19);
            list.add(data20);



        }else if(bundle.getInt(QUESTION_TYPE)==2){
            list.add(new VariantData("How much  ______  this sweater cost?","does",new String[]{"does","is","do"}));
            list.add(new VariantData("It ______  often rain in summer","doesn't",new String[]{"isn't","hasn't","doesn't"}));
            list.add(new VariantData("My mother  ______  a bad headache ","has got",new String[]{"has got","has","hav got"}));
            list.add(new VariantData("______  any brothers or sisters? ","does dad have",new String[]{"have dad got","dad has ","does dad have"}));
            list.add(new VariantData("The match … at half past nine so. I will be at home by ten o’clock","finishes",new String[]{"will finish","finishes","is finishing"}));
            list.add(new VariantData("Summer is the  ______  season of the year","Hottest",new String[]{"Hotter","Hottest","The most hot"}));
            list.add(new VariantData("We  ______  dinner yesterday","ate",new String[]{"ate","eat","were eat "}));
            list.add(new VariantData("We  ______  breakfast every day","have",new String[]{"has","having","have"}));
            list.add(new VariantData(".  . . . is a book – length fictional story","Science fiction",new String[]{"A novel","Science fiction","Detective"}));
            list.add(new VariantData(".  ______ is a person who studies people's bodies, animals and plants. ","Biologist",new String[]{"Sociologist","Economist","Biologist"}));

        }else if(bundle.getInt(QUESTION_TYPE)==3){

            list.add(new VariantData("Yangi  tarix  necha  davrga  bo`lingan?","2",new String[]{"3","2","4"}));
            list.add(new VariantData("Fransus  monarxiyasi”haqida  qaysi  olim  yozgan?","Fransua  de",new String[]{"Fransua  de","F.SHiller","G.Leoning"}));
            list.add(new VariantData("Buyuk   geografik  kashfiyotlar  atamasi  nechanchi  asr O`rtalarida  yozilgan?","15-asr  oxiri   17-asr  o`rtalarida",new String[]{"15-asr  oxiri   17-asr  o`rtalarida","15-asr  oxiri   16-asr  o`rtalarida","16-asr  oxiri   17-asr  o`rtalarida"}));
            list.add(new VariantData("Buyuk geografik  kashfiyotlarning   tashabbuskorlari   kimlar?","Portugaliya   va  Ispaniyalik  dengiz  sayyohlari",new String[]{"Hindiston  va  Yaponiya  dengiz  sayyohlar","Portugaliya   va  Ispaniyalik  dengiz  sayyohlari","To`g`ri  javob  yo`q"}));
            list.add(new VariantData("Xristafop  Kolumb  nechanchi  yillarda  yashab  o`tgan?","1451-1506",new String[]{"1462-1506","1452-1507","1451-1506"}));
            list.add(new VariantData("X. Kolumb   Hindiston  sari  suv  yo`li  ochishga  xizmat qiluvhi  ekspeditsiyani  nechanchi  yil  shartnoma  tuzishga  mufaqqat  bo`ldi?","1492-y",new String[]{"1492-y","1493-y","1498-y"}));
            list.add(new VariantData("1492-yil  12-oktyabr   qanday  kun  hisoblanadi?","Amerika  qit`asining  razman  kashf  etilgan  kuni",new String[]{"Yevropa  qit`asining  razman  kashf  etilgan  kuni","Amerika  qit`asining  razman  kashf  etilgan  kuni","Yevropaning  mustaqillik  kuni"}));
            list.add(new VariantData("Kolumb  Amerikaga  necha  marta  ekspeditsiya  uyushtirgan?","4 marta",new String[]{"6 marta","2 marta","4 marta"}));
            list.add(new VariantData("Atlantika  okeani  orqali  Hindistonga  boriladigan  dengiz  yo`li  nechanchi  yilda  kashf etildi?","1498 y",new String[]{"1605 y","1498 y","1519 y"}));
            list.add(new VariantData("Astrolobiya  qaysi  so`zdan  olingan?","lotincha",new String[]{"lotincha","arabcha","yunoncha"}));
        }
        else if(bundle.getInt(QUESTION_TYPE)==4){

            list.add(new VariantData("Geografik qobiqni eng yirik bo’lgi qaysi javobda to’g’ri?","Quruqlik, suvlik",new String[]{"Quruqlik, suvlik","Materik, okean","materik, o’lka"}));
            list.add(new VariantData("Geografik xarita nima?","yerning kichraytirib qog’ozga tushirilgan tasviri",new String[]{"yerning kichraytirilgan tasviri","yerning kichraytirib qog’ozga tushirilgan tasviri","yerning kichik modeli"}));
            list.add(new VariantData("Atlas atamasini birinchi fanga kim kiritgan?","Merkator",new String[]{"Erotosfen","Merkator","Magellan"}));
            list.add(new VariantData("Eng kichik va eng katta okenni ko’rsating?","Avstraliya  Tinch",new String[]{"Avstraliya Hind","Avstraliya Atlantika","Avstraliya  Tinch"}));
            list.add(new VariantData("Yerning umumiy maydoni qaysi javobda to’g’ri?","510mln kv km",new String[]{"510mln kv km","361mln kv km","640mln kv km"}));
            list.add(new VariantData("Quruqlik maydonini belgilang?","149mln kv km",new String[]{"149mln kv km","361mln kv km","246mln kv km"}));
            list.add(new VariantData("Tog'lardagi I yarus va 5 yarus o'simliklarini toping","Archa,mox",new String[]{"Archa,mox","Archa,xurmo","Archa,terak"}));
            list.add(new VariantData("To'qay xayvonlarini belgilang","javob",new String[]{"CHiyabo'ri,Ilon,xongul","Kiyik,Kaklik,Silovsin","Bug'u,To'ng'ich,Qirg'ovul"}));
            list.add(new VariantData("O'rta Osiyo yirtqich hayvonlarini belgilang","Qoraquloq,irbis,silovsin,sirtlon",new String[]{"Sirtlon, Ilon,Bo’ri","Qoraquloq,irbis,silovsin,sirtlon","Qoraquloq,silovsin,sug'ur"}));
            list.add(new VariantData("Qaysi o'simliklar efemirdir","Lolaquzg'aldoq",new String[]{"Lolaquzg'aldoq","Lola","SHuvoq"}));
        }
        return list;
    }
}