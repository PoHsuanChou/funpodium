BTC 後端面試題目
===================================================


## 啟動設定
將檔案解壓縮後，使用terminal 或是 cmd 進入到demo1的資料夾下
，會看到docker-compose.yml

利用指令`docker-compose up -d  --build`，就可以啟動後端以及資料庫

啟動之後可以開啟swagger ui，url是`http://localhost:8083/swagger-ui/index.html`



### API

 進入swagger ui 後，裡面會有5隻API 分別是
 1. 創建使用者 （/btc/addUser）
 2. 刪除使用者 （/btc/deleteUser）
 3. 購買BTC   （/btc/buyBtc）
 4. 售出BTC    (/btc/sellBtc)
 5. 查詢使用者紀錄（/btc/searchUser）

點入想測試的api，然後 點選`try it out ` 輸入想輸入的資料JSON 資料格式
