<template>
	<div class="wrapper">
	<header>
		<p>流水</p>
	</header>
	<ul class="transcation">
		<li v-for="item in transcationArr" >
			<div class="trancation-info">
				<p>
		流水编号：{{item.id}}    类型：{{item.type}}    数额：{{item.amount}}   创建时间：{{item.createTime}}   过期时间：{{item.expiredTime}}
				</p>
			</div>
		</li>
	</ul>
	</div>
</template>

<script>
	export default{
		name:'Transcation',
		data(){
			return {
			transcationArr:[],
			}
		},
	created(){

		this.user = this.$getSessionStorage('user');
		
		this.$axios.post('BonuspointsController/listtransactionbyid', this.$qs.stringify({
			userid: this.user.userId
		})).then(response => {
			let result = response.data;
			//console.log(result);
			this.transcationArr = result;
			
		}).catch(error => {
			console.error(error);
		});
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
</style>
