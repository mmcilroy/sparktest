
function onConnect(session) {
}

function onReceive(session, message) {
    if (message.getType()=='A') {
        session.send([[35,'A'], [98,0], [108,30]]);
    }
}
