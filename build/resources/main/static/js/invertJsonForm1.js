var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');
$(document).ready(function () {
    $(document).on("click", "#btn-save", function () {
        if (!$("#name").val()) {
            alert('이름을 입력해 주세요');
        } else if (!$("#team").val()) {
            alert('팀이름을 입력해 주세요');
        } else if (!$("#place").val()) {
            alert('근무지를 입력해 주세요');
        } else {
            var data = {
                NAME: $("#name").val(),
                TEAM: $("#team").val(),
                BUILDING: $("#place").val(),
            };
            $.ajax({
                type: 'POST',
                url: '/slack/v1/join',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                async: true//동기처리 (기본이 true)

            }

                ).done(function (){
                    console.log("success");
                    location.replace('/main');

            }).fail(function (error) {
                alert(JSON.stringify(error));
            })

        }
    });
});
