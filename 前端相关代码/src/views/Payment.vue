<template>
	<div class="wrapper">
		<!-- header部分 -->
		<header>
			<p>在线支付</p>
		</header>
		<!-- 订单信息部分 -->
		<h3>订单信息：</h3>
		<h4>大于100元的订单可获得10积分噢！</h4>
		<div class="order-info">
			<p>
				{{orders.business.businessName}}
				<i class="fa fa-caret-down" @click="detailetShow"></i>
			</p>
			<p>&#165;{{orders.orderTotal}}</p>
			<p>使用100点积分后的价格（打七折）：&#165;{{orders.orderTotal*0.7}}</p>
		</div>
		<!-- 订单明细部分 -->
		<ul class="order-detailet" v-show="isShowDetailet">
			<li v-for="item in orders.list">
				<!-- :key="item.orderId" -->
				<p>{{item.food.foodName}} x {{item.quantity}}</p>
				<p>&#165;{{item.food.foodPrice*item.quantity}}</p>
				
				
			</li>
			<li>
				<p>配送费</p>
				<p>&#165;{{orders.business.deliveryPrice}}</p>
			</li>
		</ul>
		<!-- 支付方式部分 -->
		<ul class="payment-type">
			<li>
				<img src="../assets/alipay.png">
				<i class="fa fa-check-circle"></i>
			</li>
			<li>
				<img src="../assets/wechat.png">
			</li>
		</ul>
		

		<div class="payment-button">
					<button @click="transfer">确认支付</button>      
				</div>
		    <p3>
		        
		    </p3>
		    <div class="another-button">
		      <button @click="useBonus">使用积分</button>
		    </div>

		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>
<script>
	import Footer from '../components/Footer.vue';
	var i = 0;
	export default {
		name: 'Payment',
		data() {
			return {
				virtualwallet: {},
				bonuspoints:{},
				orderId: this.$route.query.orderId,
				orders: {
					business: {}
				},
				isShowDetailet: false
			}
		},
		created() {
			// this.user = this.$getSessionStorage('user');
            
			// this.$axios.post('VirtualWalletController/getWalletbyuserId', this.$qs.stringify({
			// 	userid: this.user.userId
			// })).then(response => {
			// 	this.virtualwallet = response.data;
			// }).catch(error => {
			// 	console.error(error);
			// });
			
			// this.$axios.post('BonuspointsController/getBonuspointsbyuserId', this.$qs.stringify({
			// 	userid: this.user.userId
			// })).then(response => {
			// 	this.bonuspoints = response.data;
			// }).catch(error => {
			// 	console.error(error);
			// });
            let url = `OrdersController/getOrdersById/${this.orderId}`;
			this.$axios.get(url).then(response => {
				this.orders = response.data.result;
			}).catch(error => {
				console.error(error);
			});
		},
		mounted() {
			//这里的代码是实现：一旦路由到在线支付组件，就不能回到订单确认组件。
			//先将当前url添加到history对象中
			history.pushState(null, null, document.URL);
			//popstate事件能够监听history对象的变化
			window.onpopstate = () => {
				this.$router.push({
					path: '/index'
				});
			}
		},
		destroyed() {
			window.onpopstate = null;
		},
		methods: {


			transfer() {

				if (i == 0) {
					if (this.virtualwallet.balance < this.orders.orderTotal) {
						alert('余额不足！');
						return;
					}
					if(this.orders.orderTotal>100){
					this.$axios.post('VirtualWalletController/transfer', this.$qs.stringify({
						
							fromUserId: this.user.userId,
							toUserId:this.orders.businessId,
							amount: this.orders.orderTotal,
						
						})).catch(error => {
						console.error(error)
					});
					
					
						this.$axios.post('BonuspointsController/credit', this.$qs.stringify({
						
							userId: this.user.userId,
							amount: 10,
						
						})).then(
						alert('支付成功，且获得10积分！'),
						this.$router.go(-1)
					).catch(error => {
						console.error(error)
					});
					
					}
					else{
						this.$axios.post('VirtualWalletController/transfer', this.$qs.stringify({
						
							fromUserId: this.user.userId,
							toUserId:this.orders.businessId,
							amount: this.orders.orderTotal,
						
						})).then(
						alert('支付成功！'),
						this.$router.go(-1)
					).catch(error => {
						console.error(error)
					});
												
					}

				}
				if(i== 1)
				{
				var amout = this.orders.orderTotal*0.7;
				if (this.bonuspoints.balance < 100) {
					alert('积分不足！');
					i= 0;
					return;
				}
				if (this.virtualwallet.balance < amout) {
					alert('余额不足！');
					i= 0;
					return;
				}
				
				this.$axios.post('BonuspointsController/debit', this.$qs.stringify({
				
					userId: this.user.userId,
					amount: 100,
				
				})).catch(error => {
					console.error(error)
				});
				this.$axios.post('VirtualWalletController/transfer', this.$qs.stringify({
				
					fromUserId: this.user.userId,
					toUserId:this.orders.businessId,
					amount: this.orders.orderTotal*0.7,
				
				})).then(
					alert('使用积分支付成功！'),
					this.$router.go(-1)
				).catch(error => {
					console.error(error)
				});
				}
				// console.log(this.user);



           i= 0;
			},

			useBonus() {
			
				i = 1;
             this.transfer();
			},

			detailetShow() {
				this.isShowDetailet = !this.isShowDetailet;
			}
		},
		components: {
			Footer
		}
	}
</script>
<style scoped>
	/****************** 总容器 ******************/
	.wrapper {
		width: 100%;
		height: 100%;
	}

	/****************** header部分 ******************/
	.wrapper header {
		width: 100%;
		height: 12vw;
		background-color: #0097FF;
		color: #fff;
		font-size: 4.8vw;
		position: fixed;
		left: 0;
		top: 0;
		z-index: 1000;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	/****************** 订单信息部分 ******************/
	.wrapper h3 {
		margin-top: 12vw;
		box-sizing: border-box;
		padding: 4vw 4vw 0;
		font-size: 4vw;
		font-weight: 300;
		color: #999;
	}

	.wrapper .order-info {
		box-sizing: border-box;
		padding: 4vw;
		font-size: 4vw;
		color: #666;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order-info p:last-child {
		color: orangered;
	}

	/****************** 订单明细部分 ******************/
	.wrapper .order-detailet {
		width: 100%;
	}

	.wrapper .order-detailet li {
		width: 100%;
		box-sizing: border-box;
		padding: 1vw 4vw;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order-detailet li p {
		font-size: 3vw;
		color: #666;
	}

	/****************** 支付方式部分 ******************/
	.wrapper .payment-type {
		width: 100%;
	}

	.wrapper .payment-type li {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .payment-type li img {
		width: 33vw;
		height: 8.9vw;
	}

	.wrapper .payment-type li .fa-check-circle {
		font-size: 5vw;
		color: #38CA73;
	}

	.wrapper .payment-button {
	  width: 100%;
	  box-sizing: border-box;
	  padding: 4vw;
	}
	
	.wrapper .payment-button button {
	  width: 100%;
	  height: 10vw;
	  border: none;
	  /*去掉外轮廓线*/
	  outline: none;
	  border-radius: 10px;
	  background-color: #38CA73;
	  color: #fff;
	}
	
	.wrapper .another-button {
	  width: 100%;
	  box-sizing: border-box;
	  padding: 4vw;
	}
	
	.wrapper .another-button button {
	  width: 100%;
	  height: 10vw;
	  border: none;
	  /*去掉外轮廓线*/
	  outline: none;
	  border-radius: 10px;
	  background-color: #10d4d4;
	  color: #fff;
	}
</style>
