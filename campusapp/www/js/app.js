// Main Code File
// Run the App:
// Browser:
// cordova build browser && cordova run browser
// Android:
// cordova build && cordova run android --device

// Dom7
var $$ = Dom7;

// Web Storage
var storage = window.localStorage;

// Rest Service URLs 
// do not change default (fallback) Url
var defaultserverURL = "https://rumpelstelze.net";
// this url can get changed
var serverURL = "https://rumpelstelze.net";
var userServicePort = ":8443";
var splanServicePort = ":8443";
var cantineServicePort = ":8443";
var feedServicePort = ":8443";
// ...

// Calendar Object
var calendarModal;

// Framework7 App main instance
var app = new Framework7({
  root: '#app', // App root element
  id: 'de.hhn.mim.vs', // App bundle ID
  name: 'SAM in one', // App name
  theme: 'auto', // Automatic theme detection
  initOnDeviceReady: 'true',
  view: {
    preloadPreviousPage: false,
  },
  // Events
  on: {
    // Device Ready - Init function:
    init: function () {
      if (storage.getItem('serverURL')) {
        serverURL = storage.getItem('serverURL').toString();
      } else {
        serverURL = defaultserverURL;
      }

      // init subscription
      let subscriptionList = JSON.parse(storage.getItem("subList"));
      if (subscriptionList && subscriptionList.length > 0) {
        subscriptionList.forEach(element => {
          document.getElementById(element).checked = true;
          if (element === "Mensaplan") {
            $$('#cantine-radio-list')[0].classList.remove('disabled');
            if (storage.getItem("subCantine")) {
              let subCantineElement = document.getElementById(storage.getItem("subCantine"));
              subCantineElement.checked = true;
              let triggerEvent = new Event('change');
              subCantineElement.dispatchEvent(triggerEvent);
            } else {
              $$('[name="cantine-radio"]')[0].checked = true;
              let triggerEvent = new Event('change');
              $$('[name="cantine-radio"]')[0].dispatchEvent(triggerEvent);
            }
          }
        });
      }

      console.log('App initialized');
    },
  },
  // App root methods
  methods: {
    // ### INDEXED DB ###
    addUserToDataBase: function (userObject) {
      storage.setItem("course", userObject.studyProgram);
      // Öffnen unserer Datenbank
      let request = window.indexedDB.open('campusappDB', 1),
        db,
        tx,
        store;

      request.onerror = function (event) {
        console.log(event.target.errorCode);
      };
      // Dieses Event ist lediglich in modernen Browsern verfügbar
      request.onupgradeneeded = function (event) {
        console.log('DB Upgrade needed!');
        db = event.target.result;
        // Erstelle ein ObjectStore für diese Datenbank
        store = db.createObjectStore('UserStore', { keyPath: 'ldap' });
      };
      request.onsuccess = function (event) {
        db = request.result;
        tx = db.transaction('UserStore', 'readwrite');
        store = tx.objectStore('UserStore');

        db.onerror = function (error) {
          console.log('DB_Error: ' + error.target.errorCode);
        };
        store.put(userObject);
        tx.oncomplete = function () {
          db.close();
        };
        console.log('Object stored.')
      };
    },
    getUser: function () {
      let request = window.indexedDB.open('campusappDB', 1),
        db,
        tx,
        store;
      request.onerror = function (event) {
        console.log(event.target.errorCode);
      };
      request.onsuccess = function (event) {
        db = request.result;
        tx = db.transaction('UserStore', 'readwrite');
        store = tx.objectStore('UserStore');
        db.onerror = function (error) {
          console.log('DB_Error: ' + error.target.errorCode);
        };
        let fetch = store.get("fgreiner");
        var user;
        fetch.onsuccess = function () {
          user = fetch.result;
        };
        tx.oncomplete = function () {
          db.close();
          return user;
        };
      };
    },
    getLDAP: function () {
      return storage.getItem("username");
    },
    storeCredentials: function (ldap, pwd) {
      let key = ldap;
      // when testing in browser uuid is null...
      if (device.uuid) {
        key += device.uuid;
      } else {
        key += device.platform;
      }
      let encoding = CryptoJS.AES.encrypt(pwd, key);
      storage.setItem("hasCredentials", "true");
      storage.setItem("username", ldap);
      storage.setItem("encoding", encoding);
    },
    getPWD: function () {
      let encoding = storage.getItem("encoding");

      let key = storage.getItem("username");
      // when testing in browser uuid is null...
      if (device.uuid) {
        key += device.uuid;
      } else {
        key += device.platform;
      }
      let decrypted = CryptoJS.AES.decrypt(encoding, key);
      return decrypted.toString(CryptoJS.enc.Utf8);
    },
    loadFeedItems: function () {
      // axios call here to add latest events
      let subscriptionList = JSON.parse(storage.getItem("subList"));
      let canteenLocation = storage.getItem("subCantine");
      let username = "none";
      if (!subscriptionList || subscriptionList.length === 0) {
        // app.dialog.alert('No categories selected!');
        app.ptr.done();
        return;
      }
      if (storage.getItem("username")) {
        username = storage.getItem("username");
      }
      let responseBody = new Object();
      responseBody.canteenLocation = canteenLocation;
      responseBody.subscriptionList = subscriptionList;
      responseBody.username = username;

      axios({
        method: "post",
        url: serverURL + feedServicePort + '/api/feedRest/getAllInformation',
        data: responseBody,
        timeout: 12000, // Wait for 12 seconds
      })
        .then(function (response) {
          let feedList = response.data.feedList;
          if (feedList && feedList.length > 0) {
            let ulMediaList = document.getElementById('newsfeedMediaList');
            // remove old content
            while (ulMediaList.firstChild) {
              ulMediaList.removeChild(ulMediaList.firstChild);
            }
            feedList.forEach(feedElement => {

              let liSwipeout = document.createElement('li');
              liSwipeout.classList.add('swipeout');
              let divSwipeoutContent = document.createElement('div');
              divSwipeoutContent.classList.add('swipeout-content');
              let aItemLinkContent = document.createElement('a');
              aItemLinkContent.classList.add('item-link', 'item-content');
              if (feedElement.category === "Mensaplan") {
                aItemLinkContent.setAttribute('href', '/cantine/');
              } else {
                aItemLinkContent.setAttribute('href', '#');
              }
              let divItemMedia = document.createElement('div');
              divItemMedia.classList.add('item-media');
              divItemMedia.setAttribute("style", "text-align: center; margin: 5px");

              let iIcon = document.createElement('i');
              switch (feedElement.category) {
                case "Mensaplan":
                  iIcon.classList.add('fas', 'fa-utensils', 'fa-3x');
                  break;
                case "Veranstaltungen":
                  iIcon.classList.add('far', 'fa-newspaper', 'fa-3x');
                  break;
                case "Semesterplan":
                  iIcon.classList.add('fas', 'fa-calendar-check', 'fa-3x');
                  break;
                default:
                  iIcon.classList.add('fas', 'fa-info-circle', 'fa-3x');
                  break;
              }
              divItemMedia.appendChild(iIcon);
              aItemLinkContent.appendChild(divItemMedia);

              let divItemInner = document.createElement('div');
              divItemInner.classList.add('item-inner');
              let divItemTitleRow = document.createElement('div');
              divItemTitleRow.classList.add('item-title-row');
              let divItemTitle = document.createElement('div');
              divItemTitle.classList.add('item-title');
              divItemTitle.innerText = feedElement.title;
              divItemTitleRow.appendChild(divItemTitle);
              let divItemAfter = document.createElement('div');
              divItemAfter.classList.add('item-after');
              var today = new Date();
              var time = today.getHours() + ":" + today.getMinutes();
              divItemAfter.innerText = time;
              divItemTitleRow.appendChild(divItemAfter);
              divItemInner.appendChild(divItemTitleRow);
              let divItemSubtitle = document.createElement('div');
              divItemSubtitle.classList.add('item-subtitle');
              divItemSubtitle.innerText = feedElement.date;
              divItemInner.appendChild(divItemSubtitle);
              if (feedElement.description && feedElement.description != "null" && feedElement.description != " null") {
                let divItemText = document.createElement('div');
                divItemText.classList.add('item-text');
                divItemText.innerText = feedElement.description;
                divItemInner.appendChild(divItemText);
              }
              aItemLinkContent.appendChild(divItemInner);
              divSwipeoutContent.appendChild(aItemLinkContent);
              liSwipeout.appendChild(divSwipeoutContent);
              let divSwipeoutActions = document.createElement('div');
              divSwipeoutActions.classList.add('swipeout-actions-left');
              let aDelete = document.createElement('a');
              aDelete.classList.add('swipeout-delete');
              aDelete.setAttribute('href', '#');
              let iDeleteIcon = document.createElement('i');
              iDeleteIcon.classList.add('fas', 'fa-trash', 'fa-2x');
              aDelete.appendChild(iDeleteIcon);
              divSwipeoutActions.appendChild(aDelete);
              liSwipeout.appendChild(divSwipeoutActions);

              ulMediaList.prepend(liSwipeout);

            }); // end foreach
          }
          app.ptr.done();
        })
        .catch(function (error) {
          console.log(error);
          app.ptr.done();
        });
    }
  },
  panel: {
    swipe: 'both',
  },
  // App routes
  routes: routes,
});

// Init/Create views
var homeView = app.views.create('#view-home', {
  url: '/'
});

// In page events:
$$(document).on('page:init', function (e, page) {
  // console.log(page.name);
  if (page.name === 'home') {
    app.methods.loadFeedItems();
    console.log('returned to home screen.');
    let homeContent = document.getElementById("homeContent");
    if (!homeContent.classList.contains('ptr-content')) {
      homeContent.classList.add('ptr-content');
    }
    // Pull to refresh content
    var $ptrContent = $$('#homeContent');
    // Add 'refresh' listener on it
    $ptrContent.on('ptr:refresh', function (e) {
      app.methods.loadFeedItems();
    });
  } else {
    console.log('view: ' + page.name);
    let homeContent = document.getElementById("homeContent");
    homeContent.classList.remove('ptr-content');
    if (homeContent.children[0].className === 'ptr-preloader') {
      let ptrpre = homeContent.children[0];
      homeContent.removeChild(ptrpre);
    }
  }
});

// catch back button event on android
$$(document).on('backbutton', function (e) {
  let router = app.views.main.router;
  if (router.url != "/") {
    e.preventDefault();
    // route back
    router.back();
  } else {
    // close App
    navigator.app.exitApp();
  }
});

// handle Subscription selection
$$('[name="subCheckbox"]').on('change', function (event) {
  if (event.target.checked) {
    let subList = [];
    if (localStorage.getItem("subList")) {
      subList = JSON.parse(localStorage.getItem("subList"));
    }
    if (!subList.includes(event.target.value)) {
      subList.push(event.target.value);
      storage.setItem("subList", JSON.stringify(subList));
    }
    if (event.target.value === "Mensaplan") {
      $$('#cantine-radio-list')[0].classList.remove('disabled');
      if (storage.getItem("subCantine")) {
        let subCantineElement = document.getElementById(storage.getItem("subCantine"));
        subCantineElement.checked = true;
        let triggerEvent = new Event('change');
        subCantineElement.dispatchEvent(triggerEvent);
      } else {
        $$('[name="cantine-radio"]')[0].checked = true;
        let triggerEvent = new Event('change');
        $$('[name="cantine-radio"]')[0].dispatchEvent(triggerEvent);
      }
    }
  } else {
    let subList = [];
    if (localStorage.getItem("subList")) {
      subList = JSON.parse(localStorage.getItem("subList"));
    }
    if (subList.includes(event.target.value)) {
      var index = subList.indexOf(event.target.value);
      if (index > -1) {
        subList.splice(index, 1);
      }
      storage.setItem("subList", JSON.stringify(subList));
    }
    if (event.target.value === "Mensaplan") {
      $$('#cantine-radio-list')[0].classList.add('disabled');
    }
  }
});

// handle Cantine Selection
$$('[name="cantine-radio"]').on('change', function (event) {
  if (event.target.checked) {
    storage.setItem("subCantine", event.target.value);
  }
});