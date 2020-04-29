routes = [
  {
    path: '/',
    url: './index.html',
  },
  {
    path: '/about/',
    url: './pages/about.html',
  },
  {
    path: '/Email/',
    componentUrl: './pages/mailclient.html',
  },
  {
    path: '/cantine/',
    componentUrl: './pages/cantine.html',
  },
  {
    path: '/settings/',
    componentUrl: './pages/settings.html',
  },
  {
    path: '/ilias/',
    componentUrl: './pages/ilias.html',
  },
  {
    path: '/linda/',
    componentUrl: './pages/linda.html',
  },
  {
    path: '/splan/',
    componentUrl: './pages/splan.html',
  },
  // Page Loaders & Router
  {
    path: '/profile/',
    async: function (routeTo, routeFrom, resolve, reject) {
      // App Instance
      let app = this.app;
      // Show Preloader
      app.preloader.show();
      // Empty User Object
      var userData;
      // Check for Credentials
      let cred = window.localStorage.getItem("hasCredentials");
      if (cred === null || cred === "false") {
        app.preloader.hide();
        app.dialog.login('Enter correct username and password', 'Login', function (username, password) {
          // validate Input!
          if (username.trim() === "" || password.trim() === "") {
            app.preloader.hide();
            app.dialog.alert('Invalid Credentials.');
            return;
          }
          // store Input
          app.methods.storeCredentials(username, password);
          app.preloader.hide();
          app.loginScreen.close('#my-login-screen');
          console.log('Credentials stored in LocalStorage.');
          storage.setItem("hasCredentials", "true");

          //TODO: Avoid duplicate Code
          try {
            // Check User Service status
            axios({
              method: "get",
              url: serverURL + userServicePort + '/api/userRest/status',
              timeout: 3000, // Wait for 5 seconds
            })
              .then(function () {
                // handle success
                let uname = app.methods.getLDAP();
                let pwd = app.methods.getPWD();
                console.log('fetch data from server');
                axios.get(serverURL + userServicePort + '/api/userRest/getUserInfo', {
                  params: {
                    username: uname,
                    password: pwd
                  },
                  timeout: 5000, // Wait for 5 seconds
                })
                  .then(function (response) {
                    let user = response.data;
                    userData = user;
                    app.methods.addUserToDataBase(user);
                    console.log('User updated.');
                    // Resolve route to load page
                    app.preloader.hide();
                    resolve(
                      {
                        componentUrl: './pages/profile.html',
                      },
                      {
                        context: {
                          user: userData,
                        }
                      }
                    );
                    return;
                  })
                  .catch(function (error) {
                    console.log('Login: ' + error);
                    storage.setItem("hasCredentials", "false");
                    app.preloader.hide();
                    app.dialog.alert('Could not login! Please update your Credentials.');
                    app.loginScreen.open('#my-login-screen');
                    return;
                  });
              })
              .catch(function (error) {
                // handle error
                console.log('Status: ' + error);

                // Get User Data from local IndexedDB
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
                    db.close();
                    app.preloader.hide();
                    return;
                  };
                  let fetch = store.get(window.localStorage.getItem("username"));
                  fetch.onsuccess = function () {
                    userData = fetch.result;
                  };
                  fetch.onerror = function (error) {
                    console.log('DB:' + error);
                  };
                  tx.oncomplete = function () {
                    if (!userData) {
                      app.preloader.hide();
                      app.dialog.alert('Userservice currently not available!');
                      return;
                    }
                    db.close();
                    console.log('providing Profile page with offline data.');
                    // Hide Preloader
                    app.preloader.hide();
                    resolve(
                      {
                        componentUrl: './pages/profile.html',
                      },
                      {
                        context: {
                          user: userData,
                        }
                      }
                    );
                  };
                };
              });
          }
          catch (err) {
            console.log(err);
            app.preloader.hide();
            app.dialog.alert('Are you connected to the Internet?');
            return;
          }

        }, function (username, password) {
          storage.setItem("hasCredentials", "false");
          // Callback on Cancel
          return;
        });
      } else {
        try {
          // Check User Service status
          axios({
            method: "get",
            url: serverURL + userServicePort + '/api/userRest/status',
            timeout: 3000, // Wait for 5 seconds
          })
            .then(function () {
              // handle success
              let uname = app.methods.getLDAP();
              let pwd = app.methods.getPWD();
              console.log('fetch data from server');
              axios.get(serverURL + userServicePort + '/api/userRest/getUserInfo', {
                params: {
                  username: uname,
                  password: pwd
                },
                timeout: 5000, // Wait for 5 seconds
              })
                .then(function (response) {
                  let user = response.data;
                  userData = user;
                  app.methods.addUserToDataBase(user);
                  console.log('User updated.');
                  // Resolve route to load page
                  app.preloader.hide();
                  resolve(
                    {
                      componentUrl: './pages/profile.html',
                    },
                    {
                      context: {
                        user: userData,
                      }
                    }
                  );
                  return;
                })
                .catch(function (error) {
                  console.log('Login: ' + error);
                  storage.setItem("hasCredentials", "false");
                  app.preloader.hide();
                  app.dialog.alert('Could not login! Please update your Credentials.');
                  app.loginScreen.open('#my-login-screen');
                  return;
                });
            })
            .catch(function (error) {
              // handle error
              console.log('Status: ' + error);

              // Get User Data from local IndexedDB
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
                  db.close();
                  app.preloader.hide();
                  return;
                };
                let fetch = store.get(window.localStorage.getItem("username"));
                fetch.onsuccess = function () {
                  userData = fetch.result;
                };
                fetch.onerror = function (error) {
                  console.log('DB:' + error);
                };
                tx.oncomplete = function () {
                  if (!userData) {
                    app.preloader.hide();
                    app.dialog.alert('Userservice currently not available!');
                    return;
                  }
                  db.close();
                  console.log('providing Profile page with offline data.');
                  // Hide Preloader
                  app.preloader.hide();
                  resolve(
                    {
                      componentUrl: './pages/profile.html',
                    },
                    {
                      context: {
                        user: userData,
                      }
                    }
                  );
                };
              };
            });
        }
        catch (err) {
          console.log(err);
          app.preloader.hide();
          app.dialog.alert('Are you connected to the Internet?');
          return;
        }
      }
    },
  },
  {
    path: '/error/',
    url: './pages/404.html',
  },
  // Default route (404 page). MUST BE THE LAST
  {
    path: '(.*)',
    url: './pages/404.html',
  },
];
