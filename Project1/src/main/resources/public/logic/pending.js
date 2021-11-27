
buildPendingTable();


async function buildPendingTable(){
	let data = await getPendingReimbursements();
	let table = document.getElementById('pendingTable')
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
