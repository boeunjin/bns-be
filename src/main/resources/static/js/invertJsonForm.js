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
                url: '/join',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                async: false, //동기처리 (기본이 true)

            }
                ).done(function () {
                alert('회원가입이 성공적으로 이루어 졌습니다.');
                window.location.href = '/main';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        }
    });
});
