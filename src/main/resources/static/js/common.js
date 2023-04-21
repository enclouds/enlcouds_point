    // 알람 메뉴 토글 (기본)
    $('.btn_side_menu').click(function () {
        console.log("메뉴창");
        $(this).addClass('open')
        $(this).parents('.top').siblings('.side_menu').addClass('open');
        $('body').addClass('scroll_off');
    });

            $('.menu_close').click(function () {
                $(this).parents('.side_menu').removeClass('open');
                $(this).parents('.side_menu').siblings('.btn_side_menu').removeClass('open');
                $('body').removeClass('scroll_off');
            });

          $('.faq_btn').click(function () {
            if($(this).hasClass('show')) {
              $(this).removeClass('show');
              $(this).siblings('.comment').removeClass('show');
            } else {
              $(this).addClass('show');
              $(this).siblings('.comment').addClass('show');
            }
          });


          $( function() {
              $( ".datepicker" ).datepicker({
                  dateFormat: "yy-mm-dd",
                  showMonthAfterYear: true ,
                  dayNamesMin: ['일','월', '화', '수', '목', '금', '토'],
                  monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                  changeMonth: true,
                  changeYear: true,
                  yearSuffix: '년',
                  showButtonPanel: true,
                  maxDate : 0
              });

              $( ".datepicker_not_max" ).datepicker({
                    dateFormat: "yy-mm-dd",
                    showMonthAfterYear: true ,
                    dayNamesMin: ['일','월', '화', '수', '목', '금', '토'],
                    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                    changeMonth: true,
                    changeYear: true,
                    yearSuffix: '년',
                    showButtonPanel: true
                });
          });

          //천단위마다 콤마 생성
          function addComma(data) {
              return data.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
          }

          //모든 콤마 제거 방법
          function removeCommas(data) {
              if(!data || data.length == 0){
              	return "";
              }else{
              	return data.split(",").join("");
              }
          }

          // 쿠키저장하기
          function setCookie(cookie_name, value, days) {
              var exdate = new Date();
              exdate.setDate(exdate.getDate() + days);
              var cookie_value = escape(value) + ((days == null) ? '' : ';    expires=' + exdate.toUTCString());
              document.cookie = cookie_name + '=' + cookie_value;
          }

          // 쿠기 얻어오기
          function getCookie(cookie_name) {
              var x, y;
              var val = document.cookie.split(';');

              for (var i = 0; i < val.length; i++) {
                  x = val[i].substr(0, val[i].indexOf('='));
                  y = val[i].substr(val[i].indexOf('=') + 1);
                  x = x.replace(/^\s+|\s+$/g, ''); // 앞과 뒤의 공백 제거하기

                  if (x == cookie_name) {
                  return unescape(y); // unescape로 디코딩 후 값 리턴
                  }
              }
          }

          var deleteCookie = function(name) {
              document.cookie = name + '=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
          }

          function chk() {
              if ($('#login-option1').is(':checked') == true) {
                  setCookie('c_userid', $("#username").val(), '100');
              } else {
                  deleteCookie('c_userid');
              }
          }

        var isEmpty = function(data) {
            if(typeof(data) === 'object'){
                if(JSON.stringify(data) === '{}' || JSON.stringify(data) === '[]'){
                    return true;
                }else if(!data){
                    return true;
                }
                return false;
            }else if(typeof(data) === 'string'){
                if(!data.trim()){
                    return true;
                }
                return false;
            }else if(typeof(data) === 'undefined'){
                return true;
            }else{
                return false;
            }
        }

        function fnFileListPopup(seq, gbn){
            var url = "/bbd/fileListPopup?seq="+gbn+"_"+seq;
            window.open(url, "filePopup", "width=600,height=600,menubar=no,toolbar=no,location=no,status=no");
        }

        function addCommas(x){
		    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",");
		}

		function replaceAll(str, searchStr, replaceStr) {
            return str.split(searchStr).join(replaceStr);
        }

        function maxLengthCheck(object){
            if (object.value.length > object.maxLength){
                object.value = object.value.slice(0, object.maxLength);
            }
        }