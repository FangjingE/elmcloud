<template>
	<div class="wrapper">
		<Header>
			<p>个人主页</p>
		</Header>
		<div class="card">
			<p>我的</p>
		</div>
		<div class="box-below">
			<p>我的id: {{user.userId}}</p>
			<router-link to="Wallet">我的钱包</router-link>
			<router-link to="Bonuspoints">我的积分</router-link>
		</div>
		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>

<script>
	import Footer from '../components/Footer.vue';
	export default {
		name: 'Mine',
		// data()
		// {
		// 	return null;
		// },
		created() {
			this.user = this.$getSessionStorage('user');
		},
		methods: {


		},
		components: {
			Footer
		}
	}
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
</style>
