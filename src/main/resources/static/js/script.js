$(document).ready(function() {
    $('#btnSend').click(function() {
        $("#btnSend").prop("disabled", true);
        // フォームのデータをJSONに変換
        var rawData = $('#contactform').serializeArray();
        var data = {};
        jQuery.each(rawData, function(i, e) {
            data[e.name] = e.value;
        });
        // Ajaxを使ってメールを送信
        $.ajax({
            type: "POST",
            url: "./sendmail",
            dataType: "json",
            data: JSON.stringify(data),
            contentType: 'application/json',
            scriptCharset: 'utf-8',
            success: function(outdata, dataType) {
                if (outdata[0] == "OK") alert("メール送信しました");
                $("#btnSend").prop("disabled", false);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("Error : " + errorThrown);
                $("#btnSend").prop("disabled", false);
            }
        });
    });
});