processData();

async function processData(){
    let pendingData = await getPendingReimbursements();
    let pastData = await getPastReimbursements();
    let table = document.getElementById('displayTable')
	

    let max = 0;
    let min = pastData[0].amount;

    for(let i = 0; i < pastData.length; i++){
        if(max < pastData[i].amount){
            max = pastData[i].amount;
        }
        if(min > pastData[i].amount){
            min = pastData[i].amount;
        }
    }

    for(let i = 0; i< 1; i++){
        let row = `<tr>
        <td> The maximum processed reimbursement is: $${max} </td>
        </tr>
		<tr>
        <td> The minimum processed reimbursement is: $${min} </td>
        </tr>`
        table.innerHTML+= row;
    }
}


async function getPendingReimbursements(){
    try{
        let res = await fetch(`http://localhost:7000/api/pendingreimbursements`)
        let data = res.json();
		console.log(data);
        return data;
    } catch (e) {
        throw (e.message);
    }
}
async function getPastReimbursements(){
    try{
        let res = await fetch(`http://localhost:7000/api/pastreimbursements`)
        let data = res.json();
		console.log(data);
        return data;
    } catch (e) {
        throw (e.message);
    }
}