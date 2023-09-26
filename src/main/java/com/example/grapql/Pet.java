package com.example.grapql;

import lombok.With;

public record Pet(@With String id, String name, String color, String ownerID) {

}
