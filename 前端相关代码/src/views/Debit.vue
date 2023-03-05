<template>
	<div class="wrapper">
		<!-- header部分 -->
		<header>
			<p>提现</p>
		</header>
		<div class="card">
		    <p>提现入口</p>
		</div>
		<!-- 表单部分 -->
		<div class="box-below">
			<p>提现金额：</p>
			<input type="text" v-model="amount" placeholder="提现金额">
			<button class="charge" @click="debit">确定</button>
		</div>	
     </div>
</template>

<script>
	export default {
		name: 'Debit',
		data() {
			return {
                virtualwallet :{},
				amount: {},

			}
		},

		created() {
			this.user = this.$getSessionStorage('user');
			this.$axios.post('VirtualWalletController/getWalletbyuserId', this.$qs.stringify({
				userid: this.user.userId
			})).then(response => {
				this.virtualwallet = response.data;
			}).catch(error => {
				console.error(error);
			});
			
		},
		methods: {
			debit() {
				if (this.virtualwallet.balance < this.amount) {
					alert('余额不足！');
					return;
				}
				this.$axios.post('VirtualWalletController/debit', this.$qs.stringify({

						userId: this.user.userId,
						amount: this.amount,
					}
				)).then(
				alert('提现成功！'),
				this.$router.go(-1)
			).catch(error => {
				console.error(error)
			});

		}
	},
	}
	// if(this.virtualwallet.balance<this.orders.orderTotal)
	// {
	// 	alert('余额不足！');
	// 	return;
	// }

	// this.$axios.post('VirtualWalletController/debit', this.$qs.stringify({

	// 	userId: this.user.userId,
	// 	amount: this.orders.orderTotal,

	// 
</script>

<style scoped>
.wrapper {
    width: 100%;
	height: 100%;
	background-image: linear-gradient(to top, rgba(255, 0, 0, 0), rgb(59, 176, 246));
}

.wrapper Header {
  display: flex;
  align-items: center;
  height: 7vw;
  font-weight: 550;
  color: #FFFFFF;
  padding-top: 3vw;
}

.card {
    display: flex;
    align-items: center;
    justify-content: space-between;   
    height: 9vw;   
    font-size: 3.5vw;
    color: #FFFFFF;
    background-color: #359cff;
    margin: 2vw 3vw 0 3vw;   
    border-radius: 10px 10px 0 0;
}

.box-below {
  display: flex;
  flex-direction: column;
  height: 35vw;
  font-size: 3vw;
  color: black;
  background-color: #FFFFFF;
  margin: 0 3vw 0 3vw;
  border-radius: 10px;
  border: solid 1px #DDD;
  bottom: 5vw;
}

.card-body {
	margin-left: 3vw;
	flex: 1;
	border-right: solid 1px #DDD;
}

.charge {
  height: 6vw;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 3vw;
  margin-top: 27vw;
  border-radius: 10px;
  color: #ffffff;
  background-color: #359cff;
  position: absolute;
  border: none;
}

</style>
