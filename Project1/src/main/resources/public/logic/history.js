buildHistoryTable();

async function buildHistoryTable(){
	let data = await getPastReimbursements();
	let table = document.getElementById('historyTable')
	
	for(let i = 0; i < data.length; i++){
		let row = `<tr>
					<td>${data[i].id}</td>
					<td>${data[i].emp_id}</td>
					<td>$${data[i].amount}</td>
					<td>${data[i].type}</td>
					<td>${data[i].date}</td>
					<td>${data[i].status}</td>
				   </tr>`
			table.innerHTML += row
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