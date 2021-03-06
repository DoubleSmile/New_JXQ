'use strict';

/**
 * Config for the router
 */

// var checkPhone= '';
// var curTime = '';
angular.module('app').service('requestService', function($http) {

    //  var baseUrl = "8/New_Model_JXQ/";
    var baseUrl = "";
    //var baseUrl = "0/New_Model_JXQ/";
    var transFn = function(data) {
        return $.param(data);
    };

    var postCfg = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        transformRequest: transFn
    };

    this.goSearch = function($state, keyword) {
        $state.go('index.searchResult', {
            keyword: keyword
        });
    };

    this.getJXdetail = function($scope) {
        var url = baseUrl + "school/getSchoolDetail.do";
        $http.get(url).success(function(data) {
            $scope.info = data;
        });

    }
    this.saveSchoolDetail = function($scope, $state, $rootScope) {
        var url = baseUrl + "school/updateSchool.do";
        //    console.log($scope);
        var info = $scope.info;
        info.schoolID = $rootScope.user.sid;
        $http.post(url, info).success(function(data) {
            //console.log("xx");
            $state.reload();
        });
    }
    this.img_upload = function($scope, $state, $rootScope) {
        var url = "image/upload.do";
        var info = $scope.info;
        info.schoolID = $rootScope.user.sid;
        $http({
            method: 'POST',
            url: 'image/upload.do',
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            data: $scope.file,
            transformRequest: function(data, headersGetterFunction) {
                return data; // do nothing! FormData is very good!
            }


        })
    }
    this.getQiNiuToken = function($scope, $rootScope) {
        var url = baseUrl + "image/getToken.do";
        $http.get(url).success(function(data) {
            $('input[name="token"]').val(data.token);

            var random = Math.floor(Math.random() * 1000);
            var schoolID = $rootScope.user.sid;

            $('.IDcardPicName').val(schoolID + "_" + random + "_IDCard.jpg");
            $('.schoolID').val(schoolID);
            //      $('.schoolID').val('10');

            $('.blPicName').val(schoolID + "_" + random + "_bl.jpg");
            $('.indexPicName').val(schoolID + "_" + random + "_indexPic.jpg");
            $('.carouselPicName_1').val(schoolID + "_" + random + "_caPic_1.jpg");
            $('.carouselPicName_2').val(schoolID + "_" + random + "_caPic_2.jpg");
            $('.carouselPicName_3').val(schoolID + "_" + random + "_blPic_3.jpg");
        });
    };
    this.search = function($scope, keyword) {
        var url = baseUrl + "getSchoolByKeyWord.do";
        $http.post(url, {
                keyWord: keyword
            })
            .then(function(response) {
                $scope.searchResult = response.data.schools; ///////// 
                //$state.go('index.searchResult',{keyword:keyword});
            }, function(x) {
                console.log("Server Error");
            });
    };
    this.cancelOrder = function($scope, $state, orderId) {
        var url = baseUrl + "user/cancelOrder.do";
        $http.post(url, {
                orderID: orderId
            })
            .then(function(response) {
                var data = response.data;
                if (data.success) {
                    alert(data.msg);
                    $state.reload();
                } else {
                    alert(data.msg);
                }
            }, function(x) {
                console.log("Server Error");
            });
    };
    this.getMyOrders = function($scope) {
        var url = baseUrl + "user/getOrders.do";
        $http.post(url, {})
            .then(function(response) {
                $scope.orders = response.data.orders;
            }, function(x) {
                console.log("Server Error");
                //$rootScope.authError = 'Server Error';
            });
    };
    this.getSchoolOrders = function($scope) {
        var url = baseUrl + "school/getOrders.do";
        $http.post(url, {})
            .then(function(response) {
                $('#datatables').dataTable({
                    "data": response.data,
                    "columns": [{
                        "data": "name"
                    }, {
                        "data": "phone"
                    }, {
                        "data": "order_time"
                    }, {
                        "data": "local"
                    }, {
                        "data": "checked"
                    }],
                    "createdRow": function(row, data, dataIndex) {

                        if (data.checked == '是') {
                            $(row).addClass('bg');
                        } else {
                            $(row).addClass('bg'); //
                        }
                    },
                    "order": [
                        [2, "desc"]
                    ]
                });


                $('#datatables tbody').on('click', 'tr', function() {

                    var name = $(this)[0].cells[0].innerHTML;
                    var phone = $(this)[0].cells[1].innerHTML;
                    $('#contactMobile').val(phone);
                    $('#contactName').val(name);
                    // $scope.contactMobile = phone;
                    // $scope.contactName = name;
                    //console.log($scope.contactMobile,$scope.contactName);
                });
                console.log(response);
            });
    };

    this.checkOrder = function($scope, $state, params) {
        var url = baseUrl + "school/acceptOrder.do";
        $http.post(url, params)
            .then(function(response) {
                alert(response.data.msg);
                $state.reload();
                //$scope.orders = response.orders;
            }, function(x) {
                //console.log("Server Error");
                $rootScope.authError = 'Server Error';
            });
    };

    this.getCheckCode = function($scope, $rootScope, mobile) {
        var url = baseUrl + "user/sendCheckCode.do";
        $http.post(url, {
                mobile: mobile
            })
            .then(function(response) {
                console.log("getcode", response);
                $scope.checkPhone = response.data.requestMobile;
                $scope.curTime = response.data.curTime;
                if (response.data.success) {
                    alert("验证码已发送到您的手机，请注意查收！");
                } else {
                    alert("获取验证码失败！");
                }

            }, function(x) {
                //$rootScope.isLogin = true;
                $rootScope.authError = 'Server Error';
            });

    };

    this.userRegister = function($scope, $rootScope, $state, params) {
        var url = baseUrl + "user/register.do";
        $http.post(url, params)
            .then(function(response) {
                if (response.data.success) {
                    alert(response.data.msg);
                    $state.go("access.userSignin");
                } else {
                    alert(response.data.msg);
                }

            }, function(x) {
                //$rootScope.isLogin = true;
                $rootScope.authError = 'Server Error';
            });
    };

    this.logout = function() {
        var type = JSON.parse(localStorage.getItem('jxq.user')).role;
        var url;
        if (type === 'user') {
            url = baseUrl + "user/logout.do";
        } else if (type === 'school') {
            var url = baseUrl + "school/logout.do";
        }

        $http.get(url).success(function(data) {
            console.log(data);
        });
    };

    this.schoolLogout = function() {
        var url = baseUrl + "school/logout.do";
        $http.get(url).success(function(data) {
            console.log(data);

        });
    }


    this.getUserInfo = function($scope) {
        var url = baseUrl + "user/getUserDetail.do";
        $http.get(url).success(function(data) {
            //console.log(data);
            $scope.detail = data;
        });
    };

    this.getSchoolInfo = function($scope) {

    };

    this.schoolLogin = function($state, $rootScope, params) {
        var url = baseUrl + "school/login.do";
        $http.post(url, params)
            .then(function(response) {
                if (!response.data.success) {
                    $rootScope.authError = response.data.msg;
                } else {
                    $rootScope.isLogin = true;
                    var user = $rootScope.user = {
                        role: response.data.type,
                        sid: response.data.schoolID,
                        name: response.data.name,
                        phone: response.data.phone
                    }

                    // console.log(user);

                    localStorage.setItem('jxq.user', JSON.stringify(user));
                    // console.log('login success');
                    $state.go('index.portal');
                }
            }, function(x) {
                //$rootScope.isLogin = true;
                $rootScope.authError = 'Server Error';
            });
    };


    this.schoolRegister = function($scope, $rootScope, $state, params) {
        var url = baseUrl + "school/register.do";
        $http.post(
                /*{
                      method: 'POST',
                      url: url,
                      data: JSON.stringify(params)
                     }*/
                url, params)
            .then(function(response) {
                // console.log(response);
                $state.go('access.schoolSignin');
            }, function(x) {
                //$rootScope.isLogin = true;
                $rootScope.authError = 'Server Error';
            });
    };

    this.userLogin = function($state, $rootScope, params) {
        var url = baseUrl + "user/login.do";
        //var url = "net/u_ModelserLogin.cu"
        $http.post(url, params)
            .then(function(response) {
                if (!response.data.success) {
                    $rootScope.authError = response.data.msg;
                } else {
                    $rootScope.isLogin = true;
                    var user = $rootScope.user = {
                        uid: response.data.userID,
                        role: response.data.type,
                        phone: response.data.phone
                    }

                    localStorage.setItem('jxq.user', JSON.stringify(user));
                    console.log('login success');
                    $state.go('index.portal');
                }
            }, function(x) {
                console.log(x);
                //$rootScope.isLogin = true;
                $rootScope.authError = 'Server Error';
            });

    }
    this.getPage = function($scope, pageId) {
        var url = baseUrl + "school/getSchoolMessage.do";
        $http.post(url, {
            curPage: pageId,
            location: "西安"
        }).success(function(data) {
            console.log("getjxpage", data);
            $scope.url = url;
            $scope.jxs = data.schools;
            var t = data.totalPage;

            $scope.totalPage = [];
            for (var i = 0; i < t; i++) {
                $scope.totalPage[i] = 0;
            };
        });
    }
    this.getJxList = function($scope) {

        var url = baseUrl + "school/getSchoolList.do";
        $http.post(url, {}).success(function(data) {
            console.log("getjxlist", data);
            $scope.xajx = data;
        }).error(function(data) {
            console.log(data);
        });;
    }

    this.getJxInfo = function($scope, jxId) {
        var url = baseUrl + "school/getSchoolByID.do";
        $http.post(url, {
            schoolID: jxId
        }).success(function(data) {
            console.log("jx", data);
            $scope.jx = data;
            $scope.myInterval = 5000;
            // for (var i = 0; i < data.serviceSchool.length; i++) {
            //   console.log("server",i,data.serviceSchool[i])
            //   $scope.jx.serviceSchool = data.serviceSchool[i];
            // };

            var slides = $scope.slides = [];
            $scope.addSlide = function(url) {
                slides.push({
                    image: url
                        // image: 'img/c' + slides.length + '.jpg'
                        //text: ['Carousel text #0', 'Carousel text #1', 'Carousel text #2', 'Carousel text #3'][slides.length % 4]
                });
            };
            for (var i = 0; i < $scope.jx.carousel.length; i++) {
                $scope.addSlide($scope.jx.carousel[i]);
            }

            $scope.oneAtATime = true;
        });
    }


    this.addOrder = function($scope, $state, params) {
        var url = baseUrl + "user/addOrder.do";

        $http.post(url, params).success(function(data) {
            //$state.go();
            if (data.success) {
                alert(data.msg);
                $state.go('index.myOrder');
            } else {
                alert(data.msg);
            }
        }).error(function(data) {
            console.log(data);
            /* Act on the event */
        });;
    }

    this.getTKbyID = function($scope, id) {
        var url = '/Test_Model/examOne/getExamByTurn.do';
        var params = {
            turn: id
        };
        $http.post(url, params).success(function(data) {
            $scope.q = data;
            console.log("tk", data);
        }).error(function(data) {
            console.log(data);
        });
    }

    this.getTKbyRandom = function($scope) {
        var url = "/Test_Model/examOne/getExamRandomly.do";
        $http.post(url, {}).success(function(data) {
            console.log("random exam", data);
            $scope.q = data;
            console.log("q", $scope.q)
                // console.log(data);
        }).error(function(data) {
            console.log(data);
        });
    }
    this.getTKbyExam = function($scope) {
        var url = "/Test_Model/examOne/imitate.do";
        $http.post(url, {}).success(function(data) {
            console.log("real exam", data);
            $scope.q = data[0];
            console.log("q", $scope.q)
                // console.log(data);
        }).error(function(data) {
            console.log(data);
        });
    }
});


angular.module('app')
    .run(
        ['$rootScope', '$state', '$stateParams',
            function($rootScope, $state, $stateParams) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
                var total = new Array;
                for (var i = 0; i <= 1080; i++) {
                    total.push(i);
                };
                $rootScope.items = total;
                console.log($rootScope);

            }
        ]
    )
    .config(
        ['$stateProvider', '$urlRouterProvider', '$httpProvider', 'JQ_CONFIG',
            function($stateProvider, $urlRouterProvider, $httpProvider, JQ_CONFIG) {

                //$httpProvider.defaults.withCredentials = true;
                //Enable cross domain calls
                $httpProvider.defaults.useXDomain = true;



                $urlRouterProvider
                    .otherwise('/index/portal');

                $stateProvider
                    .state('index', {
                        abstract: true,
                        url: '/index',
                        templateUrl: 'tpl/app.html',
                        controller: function($scope, $rootScope, $state, $cookies, $cookieStore, requestService) {
                            requestService.getJxList($scope);

                            //console.log($cookies.mobile);
                            //
                            /*var user = $rootScope.user = JSON.parse(localStorage.getItem('jxq.user'));
                            if(user){
                              $rootScope.isLogin = true;

                            }*/
                            if ($cookies['mobile'] != null || $cookies['schoolCookie'] != null) {
                                var user = $rootScope.user = JSON.parse(localStorage.getItem('jxq.user'));
                                if (user)
                                    $rootScope.isLogin = true;
                            } else {
                                //localStorage.removeItem('jxq.user');
                                //$rootScope.user = {};
                            }


                            $rootScope.logout = function() {
                                requestService.logout();
                                localStorage.removeItem('jxq.user');

                                $rootScope.isLogin = false;
                                $rootScope.user = {};
                                $state.go('index.portal');
                            }

                            $rootScope.goSearch = function() {
                                var keyword = $('#keyword').val();
                                requestService.goSearch($state, keyword);
                            }
                        }
                    })
                    .state('index.searchResult', {
                        url: '/search/:keyword',
                        templateUrl: 'tpl/searchResult.html',
                        controller: function($scope, $stateParams, requestService) {
                            var keyword = $stateParams.keyword;
                            requestService.search($scope, keyword);
                        }
                    })
                    .state('index.portal', {
                        url: '/portal',
                        templateUrl: 'tpl/portal.html',
                        controller: function($scope, requestService) {

                            $scope.myInterval = 5000;
                            var indexSlides = $scope.indexSlides = [];
                            $scope.addSlide = function() {
                                indexSlides.push({
                                    //image: 'img/c' + indexSlides.length + '.jpg',
                                    image: 'img/index_img_' + (indexSlides.length + 1) + '.jpg'
                                        //text: ['Carousel text #0','Carousel text #1','Carousel text #2','Carousel text #3'][indexSlides.length % 4]
                                });
                            };
                            for (var i = 0; i < 4; i++) {
                                $scope.addSlide();
                            }
                        }
                    })
                    //西安所有驾校
                    .state('index.xajx', {
                        url: '/xajx/:pageId',
                        templateUrl: 'tpl/jxList.html',
                        controller: function($scope, $stateParams, $http, requestService) {
                            var pageId = $stateParams.pageId;

                            //TODO:用pageId 和区域("xa")请求某个页的所有驾校
                            //一页先有16个驾校
                            //
                            requestService.getPage($scope, pageId);



                        }
                    })

                //某个驾校主页,以ID区分
                .state('index.jx', {
                        url: '/jx/:jxId',
                        templateUrl: 'tpl/jxIndex.html',
                        controller: function($scope, $stateParams, requestService) {
                            var jxId = $stateParams.jxId;
                            //根据驾校Id获取该驾校的详细信息
                            requestService.getJxInfo($scope, jxId);

                        }
                    })
                    //某个驾校下单页面,以ID区分
                    .state('index.order', {
                        url: '/order/:jxId/:jxname',
                        templateUrl: 'tpl/order.html',
                        controller: function($scope, $rootScope, $state, $stateParams, $http, requestService) {
                            $scope.jxname = $stateParams.jxname;



                            $scope.submitorder = function() {
                                $('#submitOrder').prop('disabled', 'disabled');

                                if ($rootScope.isLogin && $rootScope.user.role === "user") {

                                    var params = {
                                            schoolID: $stateParams.jxId,
                                            //userID: ,
                                            isLocal: $scope.isLocal,
                                            contactName: $scope.contactName,
                                            contactMobile: $scope.contactPhone
                                        }
                                        // console.log(params);
                                    requestService.addOrder($scope, $state, params);
                                } else {
                                    alert("请先登陆哦！");
                                    $state.go('access.userSignin');
                                }
                            }


                        }
                    })

                //需要用户登录
                .state('index.profile', {
                        url: '/profile',
                        templateUrl: 'tpl/profile.html',
                        controller: 'XeditableCtrl',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function($ocLazyLoad) {
                                    return $ocLazyLoad.load('xeditable').then(function() {
                                        return $ocLazyLoad.load('js/controllers/xeditable.js');
                                    });
                                }
                            ]
                        }
                    })
                    .state('index.myOrder', {
                        url: '/myOrder',
                        templateUrl: 'tpl/myOrder.html',
                        controller: function($scope, $state, $stateParams, requestService) {

                            //var userId = $stateParams.userid;
                            requestService.getMyOrders($scope, $state);

                            $scope.cancelOrder = function(index) {
                                var orderId = $scope.orders[index].orderID;
                                requestService.cancelOrder($scope, $state, orderId);
                            };
                        }
                    })

                //需要驾校登录,驾校管理页面
                .state('index.manager', {
                    url: '/manager',
                    templateUrl: 'tpl/manager.html',
                    controller: function($scope, $state, requestService) {
                        requestService.getSchoolOrders($scope);

                        $scope.checkOrder = function() {
                            requestService.checkOrder($scope, $state, {
                                contactMobile: $('#contactMobile').val(),

                                contactName: $('#contactName').val(),
                                orderNumber: $('#orderNumber').val()
                            })
                        }


                    }
                })

                .state('index.jxEdit', {
                    url: '/schoolPublish',
                    templateUrl: 'tpl/jxEdit.html',
                    resolve: {
                        deps: ['uiLoad',
                            function(uiLoad) {
                                return uiLoad.load(['js/controllers/img-upload.js']);
                            }
                        ]
                    },
                    controller: function($scope, $http, $state, $rootScope, requestService) {
                        requestService.getJXdetail($scope);

                        // requestService.getQiNiuToken($scope,$rootScope);
                        $scope.saveSchoolDetail = function() {
                            console.log($scope);
                            requestService.saveSchoolDetail($scope, $state, $rootScope);
                        }
                        $scope.img_upload = function() {
                            requestService.img_upload($scope, $state, $rootScope);
                        }
                    }
                })

                //需要管理员登录
                .state('index.admin', {
                    url: '/admin/:userid',
                    templateUrl: 'tpl/admin.html'
                })

                .state('access', {
                        url: '/access',
                        template: '<div ui-view class="fade-in-right-big smooth"></div>'
                    })
                    .state('access.userSignin', {
                        url: '/userSignin',
                        templateUrl: 'tpl/page_usersignin.html',
                        controller: function($scope, $state, $timeout, $cookies, $cookieStore, $rootScope, requestService) {
                                $rootScope.user = {};
                                $rootScope.authError = null;
                                $scope.login = function() {
                                    $rootScope.authError = null;
                                    // Try to login

                                    var params = {
                                        phone: $scope.login.phone,
                                        password: $scope.login.password
                                    }
                                    console.log(params);
                                    requestService.userLogin($state, $rootScope, params);


                                };
                            }
                            /*resolve: {
                                deps: ['uiLoad',
                                  function( uiLoad ){
                                    return uiLoad.load( ['js/controllers/signin.js'] );
                                }]
                            }*/
                    })
                    .state('access.userSignup', {
                        url: '/userSignup',
                        templateUrl: 'tpl/page_usersignup.html',
                        controller: function($scope, $rootScope, $http, $state, requestService) {
                            $scope.user = {};
                            $scope.authError = null;
                            $scope.getSignupCodeError = null;
                            $scope.gettingCode = false;

                            $scope.getSignupCode = function() {


                                requestService.getCheckCode($scope, $rootScope, $scope.user.phone);

                            };

                            $scope.signup = function() {
                                //$scope.authError = null;
                                // Try to create
                                requestService.userRegister($scope, $rootScope, $state, {
                                    mobile: $scope.user.phone,
                                    checkCode: $scope.user.signupCode,
                                    password: $scope.user.password,
                                    requestMobile: $scope.checkPhone,
                                    curTime: $scope.curTime
                                });
                                /* $http.post('api/signup', {
                                     phone: $scope.user.phone,
                                     signupCode: $scope.user.signupCode,
                                     password: $scope.user.password
                                   })
                                   .then(function(response) {
                                     if (!response.data.user) {
                                       $scope.authError = response;
                                     } else {
                                       $state.go('index.portal');
                                     }
                                   }, function(x) {
                                     $scope.authError = 'Server Error';
                                   });*/
                            };
                        }

                    })
                    .state('access.schoolSignin', {
                        url: '/schoolSignin',
                        templateUrl: 'tpl/page_schoolsignin.html',
                        controller: function($scope, $http, $state, $rootScope, requestService) {

                                $scope.user = {};
                                $scope.authError = null;
                                $scope.signin = function() {
                                    var params = {
                                        phone: $scope.user.phone,
                                        password: $scope.user.password
                                    }
                                    requestService.schoolLogin($state, $rootScope, params);
                                }
                            }
                            /*resolve: {
                                deps: ['uiLoad',
                                  function( uiLoad ){
                                    return uiLoad.load( ['js/controllers/signin.js'] );
                                }]
                            }*/
                    })
                    .state('access.schoolSignup', {
                        url: '/schoolSignup',
                        templateUrl: 'tpl/page_schoolsignup.html',
                        controller: function($scope, $http, $rootScope, $state, requestService) {
                            $scope.user = {};
                            $scope.authError = null;
                            $scope.getSignupCodeError = null;
                            $scope.gettingCode = false;



                            $scope.signup = function() {
                                var params = {
                                    mobile: $scope.user.phone,
                                    schoolName: $scope.user.schoolName,
                                    password: $scope.user.password
                                };

                                requestService.schoolRegister($scope, $rootScope, $state, params);
                            }


                        }
                    })
                    .state('access.forgotpwd', {
                        url: '/forgotpwd',
                        templateUrl: 'tpl/page_forgotpwd.html',
                        controller: function($scope, $http, $rootScope, $state, requestService) {
                            $scope.resetpwd = function() {
                                $http.post("/Test_Model/user/sendResetCode.do", {
                                    "mobile": $scope.mobile
                                }).success(function(data) {
                                    $scope.data = data;
                                    alert(data.msg);
                                    console.log(data);
                                })
                            }
                            $scope.resetsub = function() {
                                var reset = {
                                    requestMobile: $scope.data.requestMobile,
                                    password: $scope.user.password,
                                    checkCode: $scope.user.signupCode,
                                    mobile: $scope.mobile,
                                    curTime: $scope.data.curTime
                                };
                                $http.post("/Test_Model/user/forgetPassword.do", reset).success(function(data) {
                                    console.log("reset", data);
                                    alert(data.msg);
                                    if (data.success == true) {
                                        $state.go('access.userSignin');

                                    }
                                })
                            }
                        }

                    })
                    .state('access.resetPwd', {
                        url: '/resetPwd',
                        templateUrl: 'tpl/page_resetpwd.html',
                        controller: function($scope, $http, $rootScope, $state, requestService) {
                            $scope.reset = function() {
                                var reset = {
                                    mobile: $scope.user.phone,
                                    oldPassword: $scope.user.oldPassword,
                                    newPassword: $scope.user.password
                                }
                                $http.post("/Test_Model/user/resetPassword.do", reset).success(function(data) {
                                    console.log("pass", data);
                                    alert(data.msg);
                                    if (data.success == true) {
                                        $state.go('index.portal');

                                    }

                                })
                            }

                        }
                    })
                    .state('access.404', {
                        url: '/404',
                        templateUrl: 'tpl/page_404.html'
                    })
                    .state('index.tk', {
                        url: '/tk',
                        templateUrl: 'tpl/blank.html'

                    })
                    .state('index.tk.inorder', {
                        url: '/inorder/:tid',
                        views: {
                            '@index': {
                                templateUrl: 'tpl/tk_inorder.html',
                                controller: function($scope, $state, $stateParams, requestService, $rootScope) {
                                    var tid;

                                    tid = parseInt($stateParams.tid);
                                    console.log("tid", tid);

                                    requestService.getTKbyID($scope, tid);

                                    $scope.items = $rootScope.items;
                                    $scope.unchecked = true;

                                    if (!tid || tid < 0 || tid > 1080) {
                                        return;
                                    }

                                    $scope.checkAnswer = function(answer) {

                                        $scope.right = answer == $scope.q.answer;
                                        $scope.unchecked = false;

                                    }
                                    $scope.choose = function(index) {
                                        requestService.getTKbyID($scope, index);
                                        $scope.unchecked = true;
                                        tid = index;
                                    }
                                    $scope.next = function() {
                                        // $state.go('index.tk.inorder', {
                                        //     tid: tid + 1
                                        // });
                                        $scope.unchecked = true;

                                        tid = tid + 1;
                                        requestService.getTKbyID($scope, tid);
                                    }

                                    $scope.previous = function() {
                                        if (tid <= 1)
                                            return;
                                        // $state.go('index.tk.inorder', {
                                        //     tid: tid - 1
                                        // });
                                        $scope.unchecked = true;

                                        tid = tid - 1;
                                        requestService.getTKbyID($scope, tid);

                                    }

                                }
                            }
                        }

                    })

                .state('index.tk.random', {
                        url: '/random',
                        views: {
                            '@index': {
                                templateUrl: 'tpl/tk_random.html',
                                controller: function($scope, $state, $stateParams, requestService) {

                                    requestService.getTKbyRandom($scope);

                                    $scope.unchecked = true;

                                    $scope.checkAnswer = function(answer) {

                                        $scope.right = answer == $scope.q.answer;
                                        $scope.unchecked = false;
                                    }

                                    $scope.next = function() {
                                        $state.reload();
                                    }

                                }
                            }
                        }

                    })
                    .state('index.tk.exam', {
                        url: '/random',
                        views: {
                            '@index': {
                                templateUrl: 'tpl/tk_exam.html',
                                controller: function($scope, $state, $stateParams, requestService) {

                                    requestService.getTKbyExam($scope);

                                    $scope.unchecked = true;

                                    $scope.checkAnswer = function(answer) {

                                        $scope.right = answer == $scope.q.answer;
                                        $scope.unchecked = false;
                                    }

                                    $scope.next = function() {
                                        $state.reload();
                                    }

                                }
                            }
                        }

                    })
                    //预约车
                    .state('index.ordercar', {
                        url: '/ordercar',
                        templateUrl: 'tpl/ordercar.html'
                    })
            }

        ]
    );
