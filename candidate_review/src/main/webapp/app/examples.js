/*
* JS Legenda pre nechapavkov
* [] - pole
* {} - objekt
* blabla: - parameter objektu bla bla
* */

/*
* vo všetkých requestoch treba pocitat s tym ze tam bude este autorizacia (okrem get Tests)
* */
/*getTests - vracia iba public testy .../getTests/  */
[{ //pole objektov
    id:1,
    name:"Test for Java Senior Dev",
    position:"Senior JavaDev",
    info:"Bla bla bla bla"
  },{
    id:2,
    name:"Test for ASP.NET Senior Dev",
    position:"Senior ASP.NET Dev",
    info:"Bla bla bla bla"
  }//...
];

/*getMyTests - vracia iba public testy .../getMyTests/ */
[{ //pole objektov
  id:1,
  name:"Test for Java Senior Dev",
  position:"Senior JavaDev",
  info:"Bla bla bla bla"
},{
  id:2,
  name:"Test for ASP.NET Senior Dev",
  position:"Senior ASP.NET Dev",
  info:"Bla bla bla bla"
}//...
];

/*getTest?id=y - vracia iba public testy*/
var x ={
  id: 1,
  name: "Test for Java Senior Dev",
  position: "Senior JavaDev",
  info: "Bla bla bla bla",
  visible: true,
  questions: [ //pole objektov
    {//id, test_id, category_id, type, question, level, code, image_url, language
      id:1,
      category_id:1,
      question: 'Check question',
      type: 'checkbox',
      level:3,
      code:'',
      image_url:'',
      language:'java',
      options: [{
        id: 1,
        option: 'option 1'
      }, {
        id: 2,
        option: 'option 2'
      }]
    },
    {
      id:2,
      category_id:2,
      question: 'Combo question',
      type: 'combobox',
      level:2,
      code:'',
      image_url:'',
      language:'java',
      options: [{
        id: 3,
        option: 'option 1'
      }, {
        id: 4,
        option: 'option 2'
      }]
    }

  ]
};
/*processTest?id=y - vracia iba public testy*/
{// este CVčko jak budeme posielať :/
  name:'Jožko',
  surname:'Skúšaný',
  email:'jozko.skusany@mail.com',
  date:'19-10-2015',
  total_time:'8945',//seconds
  result: {
    id: 1,
    name: "Test for Java Senior Dev",
    position: "Senior JavaDev",
    info: "Bla bla bla bla",
    questions: [ //pole objektov
      {//id, test_id, category_id, type, question, level, code, image_url, language
        id:1,
        test_id:1,
        category_id:1,
        question: 'Check question',
        type: 'checkbox',
        level:3,
        code:'',
        image_url:'',
        language:'java',
        time:54,
        google_time:33,
        options: [{
          id: 1,
          option: 'option 1',
          checked: false
        }, {
          id: 2,
          option: 'option 2',
          checked: true
        }],
        result: ''
      },
      {
        id:2,
        test_id:1,
        category_id:2,
        question: 'Combo question',
        type: 'combobox',
        level:2,
        code:'',
        image_url:'',
        language:'java',
        time:54,
        google_time:33,
        options: [{
          id: 3,
          option: 'option 1',
          checked: true
        }, {
          id: 4,
          option: 'option 2',
          checked: false
        }],
        result: ''
      },
      {
        id:3,
        test_id:1,
        category_id:2,
        question: 'dopln dačo',
        type: 'text', //abo code
        level:2,
        code:'',
        image_url:'',
        language:'java',
        time:64,//jak dlho bol na tej otazke
        google_time:33, //jak dlho bol mimo karty
        options: [],
        result: 'toto je vysledok bla bla bla'
      }

    ]
  }
};

/*post, put, delete - podla toho ci test vytvaram, editujem alebo mazem, preto mi netreba v teste flag :)*/
{
  id: 1, //id nebude ked test vytvaram :)
  name: "Test for Java Senior Dev",
  position: "Senior JavaDev",
  info: "Bla bla bla bla",
  questions: [ //pole objektov
  {//id, test_id, category_id, type, question, level, code, image_url, language
    id:1, //idčko bude mat iba ak action (nižšie) nebude create
    category_id:1,
    question: 'Check question',
    type: 'checkbox',
    level:3,
    code:'',
    image_url:'',
    language:'java',
    action:'edit', //edit, create, delete, copy
    options: [{
      id: 1, //idčko bude mat iba ak action (nižšie) nebude create
      option: 'option 1',
      truth: true,
      action:'edit' //edit, create, delete, copy
    }, {
      id: 2, //idčko bude mat iba ak action (nižšie) nebude create
      option: 'option 2',
      truth: false,
      action: 'edit'
    }]
  },
  {
    id:2,//idčko bude mat iba ak action (nižšie) nebude create
    category_id:2,
    question: 'Combo question',
    type: 'combobox',
    level:2,
    code:'',
    image_url:'',
    language:'java',
    action:'edit', //edit, create, delete
    options: [{
      id: 3,//idčko bude mat iba ak action (nižšie) nebude create
      option: 'option 1',
      truth: false,
      action: 'edit'
    }, {
      id: 4,//idčko bude mat iba ak action (nižšie) nebude create
      option: 'option 2',
      truth: true,
      action: 'edit'
    }]
  }

]
};

// Questions klasicky REST - post, get, put, delete - .../questions/[<id>/]
{
  id: 2,//idčko bude mat iba ak action (nižšie) nebude create
  category_id: 2,
  question: 'Combo question',
  type: 'combobox',
  level: 2,
  code: '',
  image_url: '',
  language: 'java',
  options: [{//ak bude type code alebo text tak toto pole bude prazdne tj. []
    id: 3,//idčko bude mat iba ak action (nižšie) nebude create
    option: 'option 1',
    truth: false,
    action: 'edit'
  }, {
    id: 4,//idčko bude mat iba ak action (nižšie) nebude create
    option: 'option 2',
    truth: true,
    action: 'edit'
  }]
};
// Categories klasicky REST - post, get, put, delete - .../questions/[<id>/]


/*
* question_result - zmazat
* column_8 vyhodit
* do candidates + ktory drži infošky o vysledku testu ... časy, body a pdoobne
*
* */
