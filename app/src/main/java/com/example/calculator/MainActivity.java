package com.example.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  private boolean isPlus = true;
  private boolean isDots = true;
  private boolean isRotate = true;
  private boolean isOpereted = true;
  private boolean negative = true;
  private int index = 0;
  private int scb = 0;
  private Button b0;
  private Button b1;
  private Button b2;
  private Button b3;
  private Button b4;
  private Button b5;
  private Button b6;
  private Button b7;
  private Button b8;
  private Button b9;
  private Button sqrt;
  private Button sin;
  private Button cos;
  private Button tan;
  private Button ln;
  private Button log;
  private Button pi;
  private Button exp;
  private Button b_plus;
  private Button b_clear;
  private Button plus;
  private Button minus;
  private ImageButton button_del;
  private Button del;
  private Button umnoj;
  private Button result;
  private Button dots;
  private Button button_skobka;
  private Button b_percent;
  private TextView textView;
  private Toast toast = null;
  private String display = "";
  private String operand = "";

  ScriptEngine scriptEngine;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findById();
    scriptEngine = new ScriptEngineManager().getEngineByName("rhino");
    setOnClickListeners();
    toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
    display = textView.getText().toString();
  }

  private void findById() {
    b0 = findViewById(R.id.button0);
    b1 = findViewById(R.id.button1);
    b2 = findViewById(R.id.button2);
    b3 = findViewById(R.id.button3);
    b4 = findViewById(R.id.button4);
    b5 = findViewById(R.id.button5);
    b6 = findViewById(R.id.button6);
    b7 = findViewById(R.id.button7);
    b8 = findViewById(R.id.button8);
    b9 = findViewById(R.id.button9);
    sqrt = findViewById(R.id.sqrt);
    pi = findViewById(R.id.pi);
    exp = findViewById(R.id.exp);
    sin = findViewById(R.id.sin);
    cos = findViewById(R.id.cos);
    tan = findViewById(R.id.tan);
    ln = findViewById(R.id.ln);
    log = findViewById(R.id.log);
    button_del = findViewById(R.id.button_del);
    plus = findViewById(R.id.button_add);
    minus = findViewById(R.id.button_sub);
    umnoj = findViewById(R.id.button_multi);
    del = findViewById(R.id.button_divide);
    b_plus = findViewById(R.id.button_znak);
    b_clear = findViewById(R.id.button_clear);
    result = findViewById(R.id.button_equal);
    dots = findViewById(R.id.button_dot);
    button_skobka = findViewById(R.id.button_skobka);
    b_percent = findViewById(R.id.b_percent);
    textView = findViewById(R.id.text);
    }

  private void setOnClickListeners() {
    b0.setOnClickListener(this);
    b1.setOnClickListener(this);
    b2.setOnClickListener(this);
    b3.setOnClickListener(this);
    b4.setOnClickListener(this);
    b5.setOnClickListener(this);
    b6.setOnClickListener(this);
    b7.setOnClickListener(this);
    b8.setOnClickListener(this);
    b9.setOnClickListener(this);
    sqrt.setOnClickListener(this);
    pi.setOnClickListener(this);
    exp.setOnClickListener(this);
    sin.setOnClickListener(this);
    cos.setOnClickListener(this);
    tan.setOnClickListener(this);
    ln.setOnClickListener(this);
    log.setOnClickListener(this);
    button_del.setOnClickListener(this);
    plus.setOnClickListener(this);
    minus.setOnClickListener(this);
    umnoj.setOnClickListener(this);
    del.setOnClickListener(this);
    b_clear.setOnClickListener(this);
    b_plus.setOnClickListener(this);
    dots.setOnClickListener(this);
    result.setOnClickListener(this);
    button_skobka.setOnClickListener(this);
    b_percent.setOnClickListener(this);
}

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
      switch (view.getId()) {
        case R.id.button0: addNum("0"); break;
        case R.id.button1: addNum("1"); break;
        case R.id.button2: addNum("2"); break;
        case R.id.button3: addNum("3"); break;
        case R.id.button4: addNum("4"); break;
        case R.id.button5: addNum("5"); break;
        case R.id.button6: addNum("6"); break;
        case R.id.button7: addNum("7"); break;
        case R.id.button8: addNum("8"); break;
        case R.id.button9: addNum("9"); break;
        case R.id.button_znak: setZnak(); break;
        case R.id.button_add: addOperator("+"); break;
        case R.id.button_sub: addOperator("-"); break;
        case R.id.button_multi: addOperator("×"); break;
        case R.id.button_divide: addOperator("÷"); break;
        case R.id.button_equal: result(); break;
        case R.id.button_del: delTextView(); break;
        case R.id.button_dot: addDots(); break;
        case R.id.button_skobka: addSkobka(); break;
        case R.id.sqrt: EngineerCalc("sqrt"); break;
        case R.id.pi: EngineerCalc("pi"); break;
        case R.id.exp: EngineerCalc("exp"); break;
        case R.id.sin: EngineerCalc("sin"); break;
        case R.id.cos: EngineerCalc("cos"); break;
        case R.id.tan: EngineerCalc("tan"); break;
        case R.id.ln: EngineerCalc("ln"); break;
        case R.id.log: EngineerCalc("log"); break;
        case R.id.b_percent: addOperator("%"); break;
        case R.id.button_clear: {
          reset();
          break;
        }
      }
    }

    public void addNum(String num) {
    if (getLastSymbol() == ')') {
      display = display + "×";
      textView.setText(display + num);
    }
     else {
        if (display.equals("0") && Integer.parseInt(num) > 0) {
          display = display + "." + num;
          textView.setText(display);
          isDots = false;
        } else {
          if (getLength() < 16) {
            display = textView.getText() + num;
            if (display.length() > 1 && display.toCharArray()[display.length() - 2] == '%') {
              display = new StringBuffer(display).insert(display.length() - 1, "×").toString();
            }
            if (isCheckAdd()) {
              isOpereted = true;
              textView.setText(display);
              negative = true;
            }
          } else {
            errorInfo("макс длина 15 символов");
          }
        }
      }
    }

    public void setZnak() {
    if (display.length() != 0 && !display.equals("0") && operand.equals("")) {
      if (isPlus) { textView.setText("-" + textView.getText());
      } else textView.setText(textView.getText().toString().substring(1));
      display = textView.getText().toString();
      isPlus = !isPlus;
    }
    else {
      if (!checkIsLastOperand()) {
        if (negative) index = display.lastIndexOf(operand);
        if (index < display.length()) {
          if (negative) {
            display = new StringBuffer(display).insert(index + 1, "(-").toString();
            scb++;
            textView.setText(display);
            negative = false;
          } else {
            display = new StringBuffer(display).deleteCharAt(index+1).toString();
            display = new StringBuffer(display).deleteCharAt(index+1).toString();
            textView.setText(display);
            negative = true;
          }
        }
      }

    }
    }

    public void addOperator(String str) {
      char s = getLastSymbol();
      if (!display.equals(""))
      {
        if (getLastSymbol() == '.') delTextView();

       if (getLastSymbol() == '%' && str != "%" ) {
         textView.setText(display + str);
         display = textView.getText().toString();}
        if (isOpereted && s != '(') {
          textView.setText(display+ (s == ')' ? ")" : "") +str);
          display = textView.getText().toString();
          isOpereted = !isOpereted;
          operand = str;
          isDots = true;
        }

        else if (( s == '+' || s == '-' || s == '×' || s == '÷' ) && (str != "%")) {
          {
          textView.setText(delLastSymbol() + str);
          display = textView.getText().toString();
          }
        }
      }
    }

    public boolean isCheckAdd(){
      if (!display.equals("")) {
        if (display.equals("00")) return false;
      }
      return true;
    }

    public void reset() {
      textView.setText("");
      isPlus = true;
      display = "";
      operand = "";
      isOpereted = true;
      isDots = true;
      scb = 0;
    }

    public void result() {
    String str = "";
      if (textView.getText().length() != 0 && checkOperand()) {
        try {
          str = textView.getText().toString().replaceAll("×","*").replaceAll("÷","/")
                  .replaceAll("%","/100").replaceAll("√","Math.sqrt")
                  .replaceAll("sin","Math.sin")
                  .replaceAll("tan","Math.tan")
                  .replaceAll("cos","Math.cos")
                  .replaceAll("exp","Math.exp")
                  .replaceAll("ln","")
                  .replaceAll("log","Math.log10");
          while(scb > 0) {
            str = str + ")";
            scb--;
          }
          str = scriptEngine.eval(str).toString();
          BigDecimal decimal = new BigDecimal(str);
          str = decimal.setScale(8, BigDecimal.ROUND_HALF_UP).toPlainString();
          reset();
          display = str;
          textView.setText(str);
          isPlus = display.charAt(0) != '-';
          isDots = !display.contains(".");
        }
        catch (Exception e) {
          e.printStackTrace();
          reset();
          errorInfo("Недопустимый формат");
        }
        if (str.equals("Infinity"))
        {
          errorInfo("Нельзя делить на ноль");
          display = delLastSymbol();
          textView.setText(display);

        } else if (str.contains("."))
        {
          str = str.replaceAll("\\.?0*$", "");
          display = str;
          textView.setText(display);
        }

      }
    }

    public boolean checkOperand(){
      if (checkIsLastOperand()) {
        isOpereted = true;
        operand = "";
        display = delLastSymbol();
        textView.setText(display);
        negative = true;
        index = 0;
        return false;
      }
      return true;
    }

    public boolean checkIsLastOperand() {
      String str = textView.getText().toString();
      char s = 'M';
      if (str.length() > 0) s = str.toCharArray()[str.length() - 1];
      return s == '+' || s == '-' || s == '×' || s == '÷' ;
    }

    public String delLastSymbol() {
      if (!isDots && getLastSymbol() == '.') {
        isDots = true;
      }
      return textView.getText().toString().substring(0, textView.getText().toString().length() - 1);
    }

    public void delTextView(){
    if (!display.equals("")) {
      String str = textView.getText().toString();
      char s = str.toCharArray()[str.length() - 1];
      if (s == '+' || s == '-' || s == '×' || s == '÷') {
        operand = "";
        isOpereted = true;
      }
      if (s == ')') scb++;
      else scb--;
      textView.setText(delLastSymbol());
      display = textView.getText().toString();
      if (s == '+' || s == '-' || s == '×' || s == '÷') {
        operand = s+"";
        isOpereted = false;
      }
      if (display.length() == 0) reset();
    }
  }


  @Override
  protected void onSaveInstanceState(Bundle outState) {
  outState.putString("text", display);
  outState.putString("rotate", isRotate+"");
  outState.putString("isPlus", isPlus+"");
  outState.putString("scb", scb+"");
  outState.putString("isDots", isDots+"");
  outState.putString("isOpereted", isOpereted+"");
  outState.putString("negative", negative+"");
  outState.putString("operand", operand+"");
  super.onSaveInstanceState(outState);
}

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    operand = savedInstanceState.getString("operand");
    display = savedInstanceState.getString("text");
    isRotate = Boolean.parseBoolean(savedInstanceState.getString("rotate"));
    isDots = Boolean.parseBoolean(savedInstanceState.getString("isDots"));
    scb = Integer.parseInt(savedInstanceState.getString("scb"));
    isPlus = Boolean.parseBoolean(savedInstanceState.getString("isPlus"));
    negative = Boolean.parseBoolean(savedInstanceState.getString("negative"));
    textView.setText(display);
  }

  public void errorInfo(String str) {
    toast.cancel();
    toast.setText(str);
    toast.show();
  }

  @SuppressLint("SetTextI18n")
  public void addDots() {
    char s = getLastSymbol();

    if (isDots && (String.valueOf(getLastSymbol()).matches("\\d"))
            || (!display.contains(".") && (String.valueOf(getLastSymbol()).matches("\\d")))) {
      textView.setText(display+".");
      display = textView.getText().toString();
      isDots = false;
    }
  }

  public char getLastSymbol() {
    String str = textView.getText().toString();
    if (str.length() > 0) return str.toCharArray()[str.length() - 1];
    return 'o';
  }

  public int getLength() {
    if (display.length() > 15) textView.setTextSize(23);
    if (display.length() > 0) {
      int ind = 0;
      if (!operand.equals("")) {
        ind = textView.getText().toString().lastIndexOf(operand) + 1;
        return textView.getText().toString().substring(ind).length() + 1;
      }
      else return display.length();
    }
    return 0;
  }

  public void EngineerCalc(String str) {
    if (str.equals("sqrt")) {
      if (String.valueOf(getLastSymbol()).matches("[-+]?\\d+")) {
        display = display + "×√(";
      }
      else {
        if (!String.valueOf(getLastSymbol()).matches("[-+]?\\d+") && checkEng()) display = display + operand;
        display = display + "√(";
      }
      scb++;
      operand = "√(";
    }
    else if (str.equals("sin")) {
      if (String.valueOf(getLastSymbol()).matches("[-+]?\\d+")) {
        display = display + "×sin(";
      }
      else {
        if (!String.valueOf(getLastSymbol()).matches("[-+]?\\d+") && checkEng()) display = display + operand;
        display = display + "sin(";
      }
      scb++;
      operand = "sin(";
    }

    else if (str.equals("cos")) {
      if (String.valueOf(getLastSymbol()).matches("[-+]?\\d+")) {
        display = display + "×cos(";
      }
      else {
        if (!String.valueOf(getLastSymbol()).matches("[-+]?\\d+") && checkEng()) display = display + operand;
        display = display + "cos(";
      }
      scb++;
      operand = "cos(";
    }

    else if (str.equals("tan")) {
      if (String.valueOf(getLastSymbol()).matches("[-+]?\\d+")) {
        display = display + "×tan(";
      }
      else {
        if (!String.valueOf(getLastSymbol()).matches("[-+]?\\d+") && checkEng()) display = display + operand;
        display = display + "tan(";
      }
      scb++;
      operand = "tan(";
    }

    else if (str.equals("log")) {
      if (String.valueOf(getLastSymbol()).matches("[-+]?\\d+")) {
        display = display + "×log(";
      }
      else {
        if (!String.valueOf(getLastSymbol()).matches("[-+]?\\d+") && checkEng()) display = display + operand;
        display = display + "log(";
      }
      scb++;
      operand = "log(";
    }

    else if (str.equals("ln")) {
      if (String.valueOf(getLastSymbol()).matches("[-+]?\\d+")) {
        display = display + "×ln(";
      }
      else {
        if (!String.valueOf(getLastSymbol()).matches("[-+]?\\d+") && checkEng()) display = display + operand;
        display = display + "ln(";
      }
      scb++;
      operand = "ln(";
    }

    else if (str.equals("exp")) {
      if (display.length() == 0) {
        display = display+2.71828;
        isDots = false;
      } else {
        if (String.valueOf(getLastSymbol()).matches("[-+]?\\d+")) display = display + "×"+2.71828;
        else if (getLastSymbol() == '(') display = display +2.71828;
      }
    }
    else if (str.equals("pi")) {
      if (display.length() == 0) {
        display = display+ 3.14159;
        isDots = false;
      } else {
        if (String.valueOf(getLastSymbol()).matches("[-+]?\\d+")) display = display + "×"+ 3.14159;
        else if (getLastSymbol() == '(') display = display +3.14159;
      }
    }
    textView.setText(display);
  }

  public boolean checkEng() {
    return !operand.equals("sin(") && !operand.equals("√(") && !operand.equals("cos(")
            && !operand.equals("ln(") && !operand.equals("log(");
  }

  public void addSkobka() {
    char s = getLastSymbol();
    String text = textView.getText().toString();
    if (text.equals("") || s == '(') {
      text = text + "(";
      operand = "(";
      scb++;
    } else if (text.length() > 0 && String.valueOf(s).matches("[-+]?\\d+") && scb > 0) {
      text = text + ")";
      operand = ")";
      scb--;
    } else if (text.length() > 0 && s == ')' && scb != 0) {
      text = text + ")";
      operand = ")";
      scb--;
    } else if (text.length() > 0 && String.valueOf(s).matches("[-+]?\\d+") && scb == 0) {
      text = text + "×(";
      operand = "(";
      scb++;
    } else if (text.length() > 0 && s == '-' || s == '+' || s == '-' || s == '×' || s == '÷'  ) {
      text = text + "(";
      operand = "(";
      scb++;
    }
      else if (text.length() > 0 && s == '%') {
              text = text + "×(";
              operand = "×(";
              scb++;
            }
    textView.setText(text);
    }
  }
