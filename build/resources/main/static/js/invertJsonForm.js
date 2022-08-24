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
                memberName: $("#name").val(),
                memberEmail: 'test@lguplus.co.kr',
                memberTeamName: $("#team").val(),
                memberLocation: $("#place").val(),
                memberSlackId: 'ssss'
            };
            $.ajax({
                beforeSend: function(xhr) {
                    console.log("beforesent",xhr);
                    console.log(header);
                    console.log(token);

                    xhr.setRequestHeader(header, token);
                },
                type: 'POST',
                url: '/bookhub-service/member/v1/oidcMembers',
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
