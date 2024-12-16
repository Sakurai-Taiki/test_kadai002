document.addEventListener('DOMContentLoaded', function() {
    // 日付ピッカー設定
    flatpickr("input[name='fromCheckinDate']", {
        locale: 'ja',
        dateFormat: "Y-m-d",
        minDate: "today"
    });

    // 時間ピッカー設定
    flatpickr("input[name='fromCheckinTime']", {
        locale: flatpickr.l10ns.ja, // 正しい日本語ロケール
        enableTime: true,           // タイムピッカーに設定
        noCalendar: true,           // カレンダーを非表示
        dateFormat: "H:i",          // 時刻フォーマット
        time_24hr: true             // 24時間表示
    });
});