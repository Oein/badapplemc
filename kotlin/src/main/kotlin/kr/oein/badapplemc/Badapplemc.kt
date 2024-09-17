package kr.oein.badapplemc

import org.bukkit.plugin.java.JavaPlugin

class Badapplemc : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic

        getCommand("kit")?.setExecutor(CommandKit(this))

//        this.kommand {
//            register("badapple") {
//                executes {
//                    val wd = server.getWorld("world")
//                    if (wd == null) {
//                    } else{
//                        val wk = Worker(wd)
//                        wk.run()
//                    }
//                }
//            }
//        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
