# Thông tin monitor được từ điện thoại Android
## Thông tin chung
Theo trục thời gian nằm ngang, các thông tin chính thu thập bao gồm
 ![alt tag](https://github.com/nguyenvulebinh/batterystats/blob/master/general.png)
 * battery_level
	Các mốc pin từ lúc bắt đầu thống kê (Thường là khi pin 100% hoặc từ lúc reset log hệ thống) dọc theo trục thời gian
 * top
	Thống kê các app hiển thị, tương tác với người dùng
 * wifi_running
	Đo các khoảng thời gian mà wifi hoạt động
 * screen
	Đo các khoảng thời gian mà màn hình bật
 * phone_in_call
	Đo khoảng thời gian gọi điện thoại
 * running
	Đo các khoảng thời gian mà CPU hoạt động
 * mobile_radio
	Đo các khoảng thời gian mà mạng di đông (3g, 4g) và bluetooth được bật.

## Thống kê lượng điện năng tiêu thụ của từng app

![alt tag](https://github.com/nguyenvulebinh/batterystats/blob/master/power_use.png)

Ví dụ phía trên, lượng pin của điện thoại là 2900 mAh, Lượng pin đã tiêu thụ là 704 mAh. Trong đó sử dụng cho màn hình là 347 mAh, app có uid là u0a86 sử dụng 173 mAh (Thực tế đây là app Chrome), thời gian chờ Idle chiếm 6.68 mAh,...

## Thống kê chi tiết tài nguyên tiêu thụ của từng app

![alt tag](https://github.com/nguyenvulebinh/batterystats/blob/master/detail.png)
* Sử dụng wifi (Lượng dữ liệu gửi, nhận)
* Sử dụng mạng di động (Lượng dữ liệu gửi nhận)
* THời gian sử dụng Bluetooth
* Các tiến trình của app và tHời gian sử dụng CPU
* Thời gian hoạt động của app 

#Những khó khăn gặp phải khi thực hiện 

* Để lấy được thông tin như đã liệt kê ở trên, ứng dụng cần phải có quyền hệ thống. Để có quyền hệ thống cần thỏa mãn một trong hai điều kiện sau:

 - Thiết bị đã được root, như thế có thể chiếm quyền hệ thống để chạy lệnh
 - Ứng dụng được cấp quyền hệ thống, để làm được điều này thì ứng dụng phải được cài đặt trong thư mục /system/priv-app (Chỉ có những ứng dụng của nhà sản xuất hoặc ứng dụng gốc của hệ điều hành mới được cài đặt trọng thư mục này)
