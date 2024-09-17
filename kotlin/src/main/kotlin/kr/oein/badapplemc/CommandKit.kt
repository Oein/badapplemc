package kr.oein.badapplemc

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CommandKit : CommandExecutor {
    private lateinit var plgin: Badapplemc
    constructor(plgin: Badapplemc) {
        this.plgin = plgin
    }
    // This method is called, when somebody uses our command
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        val wd = Bukkit.getWorld("world")
        if (wd != null) {
            val wk = Worker(wd, plgin)
            wk.run()
        }
        return true
    }
}