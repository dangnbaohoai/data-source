SELECT * FROM jspservletjdbc.news;comment
viết ở đây

nếu ta reply 1 commet mà còn comment riêng 1 cho bài post đó  thì sao
thế nếu có 1 reply của 1 user mà còn có 1 reply khác của user khác 
đấy bây giờ sao biết ta thuộc reply của tu
ok hiểu chut chút
hỏi xí nếu bài post là 1 enty riêng mà comment cũng 1 list enty thì sao request 2 enti trong 1 lần
mà cái chat box no là 1 cái luôn luôn xuất hiện trong các page thì làm sao
nó gọi thì respon về 1 lần hay sao
thế cái chat ny phải để front -end à
thế là khi load trang mới chuyển dữ liệu xuống back-end 
nó là ý hệt facebook

thế sao biết user nào gửi conten nào
chưa kể 1 box có thể có nhiều user
thế thì load cứ gọi là thôi rồi
			boxChat
            id			1		2
			idbox		1		1	
			iduser		1		2
			content
			time
chăc ko có chat tự dộng nếu có chỉ ở bên enty khoa
chat tự dộng bên studen làm hì
cái websocket này dùng đc ko
user     1      n     comment   n      1   post
						1-1
                        idComment 				1		2		3	4
                        idUser					1		2		1	3
                        idPost					1		1		1	1
                        content v.....			abc			
                        time				
                        idParentComment			null	1		2	2
                        
                        findById(idPost)
                        
                        post.listComment = array
                        
                    array=  select *  from Comment where idPost=?
còn phần chatbox
                        