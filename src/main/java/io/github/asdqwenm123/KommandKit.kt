package io.github.asdqwenm123

import io.github.monun.kommand.KommandArgument
import io.github.monun.kommand.StringType
import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.TextColor

class KommandKit(
    plugin: SoraKit
) {
    init {
        val kitName = KommandArgument.dynamic(StringType.GREEDY_PHRASE) { _, input ->
            SoraKit.getKits(player.uniqueId).keys.firstOrNull { s -> s.equals(input) }
        }.apply {
            suggests {
                suggest(SoraKit.getKits(it.source.player.uniqueId).keys)

            }
        }

        val kit = text("KIT").color(TextColor.color(0, 255, 255))
            .append(text(" >> ").color(TextColor.color(170, 170, 170)))

        plugin.kommand {
            register("kit.save") {
                requires { isPlayer }

                then("name" to string(StringType.GREEDY_PHRASE)) {
                    executes {
                        val name: String by it
                        SoraKit.addKit(player.uniqueId, Kit(plugin, player.uniqueId, name))
                        player.sendMessage(kit.append(text(name).color(TextColor.color(0, 255,0))).append(text(" (으)로 저장되었습니다.").color(TextColor.color(255, 255, 255))))

                    }

                }
            }

            register("kit.load") {
                requires { isPlayer }
                then("name" to kitName) {
                    executes { it ->
                        val name: String by it
//                        println(player.inventory.contents.map { it?.type })
//                        for (a in SoraKit.getKit(player.uniqueId, name).inventory) {
//                            plugin.server.broadcast(text(a?.type.toString()));
//                        }
                        player.inventory.contents = SoraKit.getKit(player.uniqueId, name).inventory
                        SoraKit.getPreviousKit()[player] = name
                        player.sendMessage(kit.append(text(name).color(TextColor.color(0, 255,0))).append(text(" (이)가 로드되었습니다.").color(TextColor.color(255, 255, 255))))

                    }
                }
            }

            register("kit.delete") {
                requires { isPlayer }
                then("name" to kitName) {
                    executes {
                        val name: String by it
                        player.sendMessage(kit.append(text(name).color(TextColor.color(0, 255,0))).append(text(" (이)가 삭제되었습니다.").color(TextColor.color(255, 255, 255))))

                        SoraKit.getKits(player.uniqueId).remove(name)
                    }
                }
            }

            register("kit.list") {
                requires { isPlayer }
                executes {
                    player.sendMessage(kit.append(text("킷 목록").color(TextColor.color(255, 255, 255))))
                    for (tmp in SoraKit.getKits(player.uniqueId).keys) {
                        player.sendMessage(text(tmp))
                    }
                }
            }
        }
    }
}